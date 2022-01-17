package by.kosolobov.barbershop.model.service;

import by.kosolobov.barbershop.model.dao.UserDao;
import by.kosolobov.barbershop.util.Cryptographer;

import java.util.concurrent.locks.ReentrantLock;

public class UserService {
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

    public boolean signupUser(String username, String password, String role) {
        return UserDao.getInstance().insertUser(
                username, Cryptographer.getSaltedHash(password), role);
    }

    public boolean uniqueUsername(String username) {
        return UserDao.getInstance().selectUserByUsername(username).isEmpty();
    }

    public boolean updateUser(String username, String first, String second, String sur, String email, String phone) {
        UserDao dao = UserDao.getInstance();
        return dao.updateFirstName(username, first) &&
                dao.updateSecondName(username, second) &&
                dao.updateSurName(username, sur) &&
                dao.updateEmail(username, email) &&
                dao.updatePhone(username, phone);
    }
}
