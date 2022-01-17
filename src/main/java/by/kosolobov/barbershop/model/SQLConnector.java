package by.kosolobov.barbershop.model;

import by.kosolobov.barbershop.util.DatabasePropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class SQLConnector {
    private static final Logger log = LogManager.getLogger(SQLConnector.class);
    private static final DatabasePropertyReader reader = new DatabasePropertyReader();
    private static final String PROP_DRIVER = "db.driver";
    private static final String PROP_URL = "db.url";
    private static final String PROP_USERNAME = "db.username";
    private static final String PROP_PASSWORD = "db.password";
    private static final String DRIVER = reader.get(PROP_DRIVER);
    private static final String URL = reader.get(PROP_URL);
    private static final String USERNAME = reader.get(PROP_USERNAME);
    private static final String PASSWORD = reader.get(PROP_PASSWORD);

    static {
        try {
            Class.forName(reader.get(PROP_DRIVER));
        } catch (ClassNotFoundException e) {
            log.log(Level.FATAL, "MySQL driver \"{}\" not found: {}", DRIVER, e.getMessage(), e);
            throw new RuntimeException("Registering MySQL driver failed", e);
        }
    }

    private SQLConnector() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}