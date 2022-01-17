package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.model.entity.Service;
import by.kosolobov.barbershop.model.mapper.EntityMapper;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.sql.DatabaseNameBox.*;

public class ServiceDao {
    private static final ReentrantLock lock = new ReentrantLock();
    private static ServiceDao instance;
    private final DaoBuilder dao = new DaoBuilder();

    public static ServiceDao getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new ServiceDao();
            }
            lock.unlock();
        }
        return instance;
    }

    private ServiceDao() {}

    public List<Service> selectServiceByName(String name) {
        return selectService(COLUMN_SERVICE_NAME, name);
    }

    public List<Service> selectServiceById(String id) {
        return selectService(COLUMN_SERVICE_ID, id);
    }

    private List<Service> selectService(String column, String value) {
        return EntityMapper.mapService(dao.select(TABLE_SERVICE, COLUMNS_SERVICE)
                .where(column, value)
                .executeSql(COLUMNS_SERVICE));
    }

    public boolean updateService(String oldName, String newName) {
        return dao.update(TABLE_SERVICE)
                .set(COLUMN_SERVICE_NAME, newName)
                .where(COLUMN_SERVICE_NAME, oldName)
                .execute();
    }

    public boolean insertService(String name) {
        return dao.insert(TABLE_SERVICE, COLUMN_SERVICE_NAME)
                .values(name)
                .execute();
    }

    public boolean deleteService(String name) {
        return dao.delete(TABLE_SERVICE)
                .where(COLUMN_SERVICE_NAME, name)
                .execute();
    }
}
