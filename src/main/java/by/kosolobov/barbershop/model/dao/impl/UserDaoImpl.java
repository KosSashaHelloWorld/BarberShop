package by.kosolobov.barbershop.model.dao.impl;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.dao.DaoBuilder;
import by.kosolobov.barbershop.model.dao.UserDao;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.mapper.EntityMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.dao.DatabaseNameBox.*;

public class UserDaoImpl implements UserDao {
    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static UserDaoImpl instance;
    private final DaoBuilder dao = new DaoBuilder();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new UserDaoImpl();
            }
            lock.unlock();
        }
        return instance;
    }

    public List<User> selectAllBarbers() throws DaoException {
        return EntityMapper.mapUser(dao.select(TABLE_USER, COLUMNS_USER)
                .where(COLUMN_USER_ROLE, User.Role.BARBER.toString())
                .executeSql(COLUMNS_USER));
    }

    public Optional<User> selectUserByUsername(String username) throws DaoException {
        try {
            List<User> res = EntityMapper.mapUser(dao.select(TABLE_USER, COLUMNS_USER)
                    .where(COLUMN_USERNAME, username)
                    .executeSql(COLUMNS_USER));

            if (!res.isEmpty()) {
                return Optional.of(res.get(0));
            }

        } catch (IndexOutOfBoundsException e) {
            log.log(Level.ERROR, "SELECT: User {} does not exist: {}", username, e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<String> selectPassword(String username) throws DaoException {
        List<Map<String, String>> entities = dao.select(TABLE_USER, COLUMN_PASSWORD)
                .where(COLUMN_USERNAME, username)
                .executeSql(COLUMN_PASSWORD);
        if (!entities.isEmpty()) {
            return Optional.of(entities.get(0).get(COLUMN_PASSWORD));
        } else {
            log.log(Level.ERROR, "User {} has no password!", username);
            return Optional.empty();
        }
    }

    public boolean updatePassword(String username, String oldPass, String newPass) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_PASSWORD, newPass)
                .where(COLUMN_USERNAME, username)
                .andWhere(COLUMN_PASSWORD, oldPass)
                .execute();
    }

    public boolean updateUsername(String oldUsername, String newUsername) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_USERNAME, newUsername)
                .where(COLUMN_USERNAME, oldUsername)
                .execute();
    }

    public boolean updateFirstName(String username, String firstName) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_FIRST_NAME, firstName)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean updateSecondName(String username, String secondName) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_SECOND_NAME, secondName)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean updateSurname(String username, String surname) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_SURNAME, surname)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean updateEmail(String username, String email) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_EMAIL, email)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean updatePhone(String username, String phone) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_PHONE, phone)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean updateDescription(String username, String description) throws DaoException {
        return dao.update(TABLE_USER)
                .set(COLUMN_DESC, description)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean insertUser(User user, String password) throws DaoException {
        return dao.insert(TABLE_USER, COLUMNS_USER)
                .values(user.getUsername(), password, user.getUserRole().toString(),
                        user.getFirstName(), user.getSecondName(), user.getSurname(),
                        user.getEmail(), user.getPhone(), user.getDescription())
                .execute();
    }

    public boolean deleteUser(String username, String password) throws DaoException {
        return dao.delete(TABLE_USER)
                .where(COLUMN_USERNAME, username)
                .andWhere(COLUMN_PASSWORD, password)
                .execute();
    }
}
