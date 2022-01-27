package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceDao {
    boolean deleteService(String serviceName) throws DaoException;
    boolean insertService(String serviceName) throws DaoException;
    List<Service> selectServiceByBarber(String barberUsername) throws DaoException;
    Optional<Service> selectService(String serviceName) throws DaoException;
    boolean updateService(String oldServiceName, String newServiceName) throws DaoException;
}
