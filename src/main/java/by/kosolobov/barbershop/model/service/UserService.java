package by.kosolobov.barbershop.model.service;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.dao.UserDao;
import by.kosolobov.barbershop.util.Cryptographer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    private static UserService instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new UserService();
            }
            lock.unlock();
        }
        return instance;
    }

    public boolean signupUser(String username, String password, String role) throws ServiceException {
        Optional<String> saltedHash = Cryptographer.getSaltedHash(password);
        if (saltedHash.isPresent()) {
            try {
                return UserDao.getInstance().insertUser(
                        username, saltedHash.get(), role);
            } catch (DaoException e) {
                log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
                throw new ServiceException("Dao exception happened while signing up user", e);
            }
        } else {
            return false;
        }
    }

    public boolean uniqueUsername(String username) throws ServiceException {
        try {
            return UserDao.getInstance().selectUserByUsername(username).isEmpty();
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while checking username is unique", e);
        }
    }

    public boolean updateUser(String username, String first, String second, String sur, String email, String phone) throws ServiceException {
        UserDao dao = UserDao.getInstance();
        try {
            return dao.updateFirstName(username, first) &&
                    dao.updateSecondName(username, second) &&
                    dao.updateSurName(username, sur) &&
                    dao.updateEmail(username, email) &&
                    dao.updatePhone(username, phone);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while updating user", e);
        }
        //todo: split updating to different methods for correct client view
    }

    public boolean checkUser(String username, String password) throws ServiceException {
        try {
            Optional<String> stored = UserDao.getInstance().selectPassword(username);
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
