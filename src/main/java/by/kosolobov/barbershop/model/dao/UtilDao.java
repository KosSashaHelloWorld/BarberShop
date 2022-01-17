package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.model.entity.Service;
import by.kosolobov.barbershop.model.mapper.EntityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.sql.DatabaseNameBox.*;

public class UtilDao {
    private static final Logger log = LogManager.getLogger(UtilDao.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static UtilDao instance;

    public static UtilDao getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new UtilDao();
            }
            lock.unlock();
        }
        return instance;
    }

    private UtilDao() {}

    public List<Service> selectBarberServices(int barberId) {
        DaoBuilder dao = new DaoBuilder();

        return EntityMapper.mapServiceFull(dao.select(TABLE_BARBER_HAS_SERVICE, "*")
                .join(TABLE_SERVICE)
                .onStart(TABLE_BARBER_HAS_SERVICE + "." + COLUMN_SERVICE_ID, TABLE_SERVICE + "." + COLUMN_SERVICE_ID)
                .onEnd()
                .where(COLUMN_BARBER_ID, String.valueOf(barberId))
                .executeSql());
    }
}








