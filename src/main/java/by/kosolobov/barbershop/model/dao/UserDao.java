package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.mapper.EntityMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.sql.DatabaseNameBox.*;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static final String BARBER_ROLE = "barber";
    private static UserDao instance;
    private final DaoBuilder dao = new DaoBuilder();

    public static UserDao getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new UserDao();
            }
            lock.unlock();
        }
        return instance;
    }

    private UserDao() {}

    public List<User> selectAllBarbers() {
        return EntityMapper.mapUser(dao.select(TABLE_USER, COLUMNS_USER)
                .where(COLUMN_USER_ROLE, BARBER_ROLE)
                .executeSql(COLUMNS_USER));
    }

    public Optional<User> selectUserByUsername(String username) {
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

    public Optional<String> selectPassword(String username) {
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

    public boolean updatePassword(String username, String oldPass, String newPass) {
        return dao.update(TABLE_USER)
                .set(COLUMN_PASSWORD, newPass)
                .where(COLUMN_USERNAME, username)
                .andWhere(COLUMN_PASSWORD, oldPass)
                .execute();
    }

    public boolean updateUsername(String oldUsername, String newUsername) {
        return updateUser(oldUsername, COLUMN_USERNAME, newUsername);
    }

    public boolean updateFirstName(String username, String first) {
        return updateUser(username, COLUMN_FIRST_NAME, first);
    }

    public boolean updateSecondName(String username, String second) {
        return updateUser(username, COLUMN_SECOND_NAME, second);
    }

    public boolean updateSurName(String username, String sur) {
        return updateUser(username, COLUMN_SUR_NAME, sur);
    }

    public boolean updateEmail(String username, String email) {
        return updateUser(username, COLUMN_EMAIL, email);
    }

    public boolean updatePhone(String username, String phone) {
        return updateUser(username, COLUMN_PHONE, phone);
    }

    public boolean updateUserDesc(String username, String desc) {
        return updateUser(username, COLUMN_DESC, desc);
    }

    private boolean updateUser(String username, String column, String value) {
        return dao.update(TABLE_USER)
                .set(column, value)
                .where(COLUMN_USERNAME, username)
                .execute();
    }

    public boolean insertUser(String username, String password, String userRole) {
        return dao.insert(TABLE_USER, COLUMNS_USER_MIN)
                .values(username, password, userRole)
                .execute();
    }

    public boolean deleteUser(String username, String password) {
        return dao.delete(TABLE_USER)
                .where(COLUMN_USERNAME, username)
                .andWhere(COLUMN_PASSWORD, password)
                .execute();
    }
}
