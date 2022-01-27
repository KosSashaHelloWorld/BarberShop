package by.kosolobov.barbershop.model.service;

import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.entity.User;

public interface UserService {
    boolean checkUser(String username, String password) throws ServiceException;

    boolean signupUser(User user, String password) throws ServiceException;

    boolean uniqueUsername(String username) throws ServiceException;

    boolean updateEmail(String username, String email) throws ServiceException;

    boolean updateFirstName(String username, String firstName) throws ServiceException;

    boolean updatePassword(String username, String oldPassword, String newPassword) throws ServiceException;

    boolean updatePhone(String username, String phone) throws ServiceException;

    boolean updateSecondName(String username, String secondName) throws ServiceException;

    boolean updateSurname(String username, String surname) throws ServiceException;

    boolean updateDescription(String username, String description) throws ServiceException;

    boolean updateUsername(String oldUsername, String newUsername) throws ServiceException;
}
