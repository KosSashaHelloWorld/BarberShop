package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DaoBuilder {
    private static final Logger log = LogManager.getLogger(DaoBuilder.class);
    final StringBuilder builder = new StringBuilder();

    public DaoBuilder select(String table, String... columns) {
        builder.append("SELECT");

        for (String col : columns) {
            builder.append(" %s,".formatted(col));
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" FROM %s ".formatted(table));

        return this;
    }

    public DaoBuilder insert(String table, String... columns) {
        builder.append("INSERT %s(".formatted(table));

        for (String col : columns) {
            builder.append(" %s,".formatted(col));
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(") ");

        return this;
    }

    public DaoBuilder update(String table) {
        builder.append("UPDATE %s ".formatted(table));

        return this;
    }

    public DaoBuilder delete(String table) {
        builder.append("DELETE FROM %s ".formatted(table));

        return this;
    }

    public DaoBuilder where(String column, String value) {
        builder.append("WHERE %s = '%s' ".formatted(column, value));

        return this;
    }

    public DaoBuilder andWhere(String column, String value) {
        builder.append("AND %s = '%s' ".formatted(column, value));

        return this;
    }

    public DaoBuilder values(String... values) {
        builder.append("VALUES (");

        for (String val : values) {
            builder.append(" '%s',".formatted(val));
        }
        builder.deleteCharAt(builder.length() - 1);

        builder.append(")");

        return this;
    }

    public DaoBuilder set(String column, String value) {
        builder.append("SET %s = '%s' ".formatted(column, value));

        return this;
    }

    public DaoBuilder andSet(String column, String value) {
        builder.append(", %s = %s ".formatted(column, value));

        return this;
    }

    public DaoBuilder join(String table) {
        builder.append("JOIN %s ".formatted(table));

        return this;
    }

    public DaoBuilder onStart(String column1, String column2) {
        builder.append("ON (%s = %s".formatted(column1, column2));

        return this;
    }

    public DaoBuilder andOn(String column1, String column2) {
        builder.append(", %s = %s".formatted(column1, column2));

        return this;
    }

    public DaoBuilder onEnd() {
        builder.append(") ");

        return this;
    }

    public boolean execute() throws DaoException {
        builder.append(";");
        log.log(Level.INFO, "Executing:\n    {}", builder);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(builder.toString());
        } catch (SQLException e) {
            log.log(Level.ERROR, "EXECUTE SQL ERROR: {}", e.getMessage(), e);
            throw new DaoException("SQL exception happened while executing query", e);
        } finally {
            builder.delete(0, builder.length());
        }

        return true;
    }

    public List<Map<String, String>> executeSql(String... columns) throws DaoException {
        builder.append(";");
        List<Map<String, String>> result = new ArrayList<>();
        log.log(Level.INFO, "Executing:\n    {}", builder);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(builder.toString())) {

            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                for (String column : columns) {
                    map.put(column, resultSet.getString(column));
                }
                result.add(map);
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "EXECUTE SQL ERROR: {}", e.getMessage(), e);
            throw new DaoException("SQL exception happened while executing query", e);
        } finally {
            builder.delete(0, builder.length());
        }

        return result;
    }
}
