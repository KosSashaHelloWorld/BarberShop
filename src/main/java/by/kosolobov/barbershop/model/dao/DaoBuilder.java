package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.ConnectionPool;
import by.kosolobov.barbershop.util.validator.SqlValidator;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DaoBuilder {
    private static final Logger log = LogManager.getLogger(DaoBuilder.class);
    private final StringBuilder builder = new StringBuilder();
    private final List<String> params = new ArrayList<>();

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
        params.add(value);
        builder.append("WHERE %s = ? ".formatted(column));

        return this;
    }

    public DaoBuilder andWhere(String column, String value) {
        params.add(value);
        builder.append("AND %s = ? ".formatted(column));

        return this;
    }

    public DaoBuilder values(String... values) {
        builder.append("VALUES (");

        for (String val : values) {
            params.add(val);
            builder.append(" ?,");
        }
        builder.deleteCharAt(builder.length() - 1);

        builder.append(")");

        return this;
    }

    public DaoBuilder set(String column, String value) {
        params.add(value);
        builder.append("SET %s = ? ".formatted(column));

        return this;
    }

    public DaoBuilder andSet(String column, String value) {
        params.add(value);
        builder.append(", %s = ? ".formatted(column));

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

        String validQuery = SqlValidator.getValidQuery(builder.toString());

        log.log(Level.INFO, "\n\nExecuting:\n    {}\nWith parameters:\n    {}\n", validQuery, params);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(validQuery)) {
            int i = 1;
            for (String value : params) {
                statement.setString(i++, value);
            }
            statement.execute(validQuery);
        } catch (SQLException e) {
            log.log(Level.ERROR, "EXECUTE SQL ERROR: {}", e.getMessage(), e);
            throw new DaoException("SQL exception happened while executing query", e);
        } finally {
            builder.delete(0, builder.length());
            params.clear();
        }

        return true;
    }

    public List<Map<String, String>> executeSql(String... columns) throws DaoException {
        builder.append(";");

        String validQuery = SqlValidator.getValidQuery(builder.toString());

        List<Map<String, String>> result = new ArrayList<>();
        log.log(Level.INFO, "\n\nExecuting:\n    {}\nWith parameters:\n    {}\n", validQuery, params);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(validQuery)) {
            int i = 1;
            for (String value : params) {
                statement.setString(i++, value);
            }
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Map<String, String> map = new HashMap<>();
                    for (String column : columns) {
                        map.put(column, resultSet.getString(column));
                    }
                    result.add(map);
                }
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "EXECUTE SQL ERROR: {}", e.getMessage(), e);
            throw new DaoException("SQL exception happened while executing query", e);
        } finally {
            builder.delete(0, builder.length());
            params.clear();
        }

        return result;
    }
}
