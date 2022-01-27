package by.kosolobov.barbershop.model.dao.impl;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.dao.DaoBuilder;
import by.kosolobov.barbershop.model.dao.ServiceDao;
import by.kosolobov.barbershop.model.entity.Service;
import by.kosolobov.barbershop.model.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.dao.DatabaseNameBox.*;

public class ServiceDaoImpl implements ServiceDao {
    private static final ReentrantLock lock = new ReentrantLock();
    private static ServiceDaoImpl instance;
    private final DaoBuilder dao = new DaoBuilder();

    private ServiceDaoImpl() {
    }

    public static ServiceDaoImpl getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new ServiceDaoImpl();
            }
            lock.unlock();
        }
        return instance;
    }

    @Override
    public Optional<Service> selectService(String serviceName) throws DaoException {
        List<Service> services = EntityMapper.mapService(dao.select(TABLE_SERVICE, COLUMNS_SERVICE)
                .where(COLUMN_SERVICE_NAME, serviceName)
                .executeSql(COLUMNS_SERVICE));
        if (services.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(services.get(0));
        }
    }

    @Override
    public boolean updateService(String oldName, String newName) throws DaoException {
        return dao.update(TABLE_SERVICE)
                .set(COLUMN_SERVICE_NAME, newName)
                .where(COLUMN_SERVICE_NAME, oldName)
                .execute();
    }

    @Override
    public boolean insertService(String name) throws DaoException {
        return dao.insert(TABLE_SERVICE, COLUMN_SERVICE_NAME)
                .values(name)
                .execute();
    }

    @Override
    public List<Service> selectServiceByBarber(String barberUsername) throws DaoException {
        return EntityMapper.mapService(dao.select(TABLE_BARBER_HAS_SERVICE, COLUMNS_BARBER_HAS_SERVICE)
                .where(COLUMN_BARBER_USERNAME, barberUsername)
                .executeSql(COLUMNS_BARBER_HAS_SERVICE));
    }

    @Override
    public boolean deleteService(String name) throws DaoException {
        return dao.delete(TABLE_SERVICE)
                .where(COLUMN_SERVICE_NAME, name)
                .execute();
    }
}
