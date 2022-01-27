package by.kosolobov.barbershop.model.service.impl;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.dao.UserDao;
import by.kosolobov.barbershop.model.dao.impl.UserDaoImpl;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.service.UserService;
import by.kosolobov.barbershop.util.Cryptographer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static final UserDao dao = UserDaoImpl.getInstance();
    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new UserServiceImpl();
            }
            lock.unlock();
        }
        return instance;
    }

    @Override
    public boolean signupUser(User user, String password) throws ServiceException {
        Optional<String> saltedHash = Cryptographer.getSaltedHash(password);
        if (saltedHash.isPresent()) {
            try {
                return dao.insertUser(user, saltedHash.get());
            } catch (DaoException e) {
                log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
                throw new ServiceException("Dao exception happened while signing up user", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean uniqueUsername(String username) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().selectUserByUsername(username).isEmpty();
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while checking username is unique", e);
        }
    }

    @Override
    public boolean updateEmail(String username, String email) throws ServiceException {
        try {
            return dao.updateEmail(username, email);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating email", e);
        }
    }

    @Override
    public boolean updateFirstName(String username, String firstName) throws ServiceException {
        try {
            return dao.updateFirstName(username, firstName);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating first name", e);
        }
    }

    @Override
    public boolean updatePassword(String username, String oldPassword, String newPassword) throws ServiceException {
        try {
            return dao.updatePassword(username, oldPassword, newPassword);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating password", e);
        }
    }

    @Override
    public boolean updatePhone(String username, String phone) throws ServiceException {
        try {
            return dao.updatePhone(username, phone);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating phone number", e);
        }
    }

    @Override
    public boolean updateSecondName(String username, String secondName) throws ServiceException {
        try {
            return dao.updateEmail(username, secondName);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating second name", e);
        }
    }

    @Override
    public boolean updateSurname(String username, String surname) throws ServiceException {
        try {
            return dao.updateSurname(username, surname);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating surname", e);
        }
    }

    @Override
    public boolean updateDescription(String username, String description) throws ServiceException {
        try {
            return dao.updateDescription(username, description);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating description", e);
        }
    }

    @Override
    public boolean updateUsername(String oldUsername, String newUsername) throws ServiceException {
        try {
            return dao.updateUsername(oldUsername, newUsername);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating username", e);
        }
    }

    public boolean checkUser(String username, String password) throws ServiceException {
        try {
            Optional<String> stored = UserDaoImpl.getInstance().selectPassword(username);
            if (stored.isPresent()) {
                return Cryptographer.check(password, stored.get());
            } else {
                return false;
            }
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while checking users' password", e);
        }
    }
}
