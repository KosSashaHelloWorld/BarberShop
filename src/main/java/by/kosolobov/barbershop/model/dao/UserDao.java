package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean deleteUser(String username, String password) throws DaoException;

    boolean insertUser(User user, String password) throws DaoException;

    List<User> selectAllBarbers() throws DaoException;

    Optional<String> selectPassword(String username) throws DaoException;

    Optional<User> selectUserByUsername(String username) throws DaoException;

    boolean updateEmail(String username, String email) throws DaoException;

    boolean updateFirstName(String username, String firstName) throws DaoException;

    boolean updatePassword(String username, String oldPassword, String newPassword) throws DaoException;

    boolean updatePhone(String username, String phone) throws DaoException;

    boolean updateSecondName(String username, String secondName) throws DaoException;

    boolean updateSurname(String username, String surname) throws DaoException;

    boolean updateDescription(String username, String description) throws DaoException;

    boolean updateUsername(String oldUsername, String newUsername) throws DaoException;
}
