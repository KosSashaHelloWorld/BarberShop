package by.kosolobov.barbershop.model;

import by.kosolobov.barbershop.exception.PoolInitException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ReentrantLock locker = new ReentrantLock();
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int INITIALIZATION_TRIES = 3;
    private static final int SECOND = 1000;
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freePool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
    private final BlockingQueue<ProxyConnection> busyPool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);

    private ConnectionPool() throws PoolInitException {
        try {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                    freePool.put(new ProxyConnection());
            }
            log.log(Level.INFO, "Connection pool initialization successful");
        } catch (SQLException e) {
            log.log(Level.ERROR, "Connection pool initialization error: {}", e.getMessage(), e);
            throw new PoolInitException("Filling ConnectionPool with connections failed", e);
        } catch (InterruptedException e) {
            log.log(Level.ERROR, "Connection pool initialization interrupted: {}", e.getMessage(), e);
            throw new PoolInitException("Filling ConnectionPool with connections interrupted", e);
        }
    }

    /**
     * Double-check Singleton
     * @return Connection pool
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            locker.lock();
            for (int i = INITIALIZATION_TRIES; (instance == null) && (i > 0); i--) {
                try {
                    instance = new ConnectionPool();
                } catch (PoolInitException e) {
                    log.log(Level.ERROR, "ConnectionPool initialization error: {}", e.getMessage(), e);
                    if (i > 1) {
                        try {
                            Thread.sleep(SECOND);
                        } catch (InterruptedException ex) {
                            log.log(Level.ERROR, "ConnectionPool thread interrupted: {}", ex.getMessage(), ex);
                        }
                    }
                }
            }
            locker.unlock();
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freePool.take();
            busyPool.put(connection);
        } catch (InterruptedException e) {
            log.log(Level.ERROR, "Getting connection from connection pool interrupted: {}", e.getMessage(), e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection instanceof ProxyConnection c) {
                if (!busyPool.remove(c)) {
                    log.log(Level.WARN, "Illegal operation! Can not release connection that is not in use!");
                    //todo: check summary of connections;
                } else {
                    freePool.put(c);
                }
            } else {
                log.log(Level.WARN, "Attempt of invasion into ConnectionPool without using ProxyConnection");
            }
        } catch (InterruptedException e) {
            log.log(Level.ERROR, "Releasing connection interrupted: {}", e.getMessage(), e);
        }
    }

    public void destroy() {
        try {
            for (ProxyConnection c : freePool) {
                c.reallyClose();
            }
            freePool.clear();

            for (ProxyConnection c : busyPool) {
                c.reallyClose();
            }
            busyPool.clear();

            log.log(Level.INFO, "Destroying connection pool successful");
        } catch (SQLException e) {
            log.log(Level.WARN, "Destroying connection pool failed: {}", e.getMessage(), e);
        }
    }
}
