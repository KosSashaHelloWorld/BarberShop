package by.kosolobov.barbershop.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class DatabasePropertyReader {
    private static final Logger log = LogManager.getLogger(DatabasePropertyReader.class);
    private static final Properties properties = new Properties();
    private static final String DEFAULT_PROPERTY_PATH = "config/mysql-config.properties";

    static {
        URL url = DatabasePropertyReader.class
                .getClassLoader()
                .getResource(DEFAULT_PROPERTY_PATH);

        if (url == null) {
            log.log(Level.FATAL, "{} file does not exist, initialization failed", DEFAULT_PROPERTY_PATH);
        }

        readProperties(url);
    }

    public String get(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public void updatePropertiesFile() {
        URL url = DatabasePropertyReader.class
                .getClassLoader()
                .getResource(DEFAULT_PROPERTY_PATH);

        if (url == null) {
            log.log(Level.FATAL, "{} file does not exist, create new property file at default path", DEFAULT_PROPERTY_PATH);
            return;
        }

        readProperties(url);
    }

    public void updatePropertiesFile(String propertiesFilePath) {
        URL url = DatabasePropertyReader.class
                .getClassLoader()
                .getResource(propertiesFilePath);

        if (url == null) {
            log.log(Level.FATAL, "{} file does not exist, enter a correct property file path", propertiesFilePath);
            return;
        }

        readProperties(url);
    }

    private static void readProperties(URL url) {
        File propertyFile = new File(url.getFile());
        try (FileInputStream fis = new FileInputStream(propertyFile)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            log.log(Level.FATAL, "Properties file not found: {}", e.getMessage(), e);
        } catch (IOException e) {
            log.log(Level.FATAL, "Reading database properties failed: {}", e.getMessage(), e);
        }
        log.log(Level.INFO, "Reading property file successful: {}", url.getFile());
    }
}
