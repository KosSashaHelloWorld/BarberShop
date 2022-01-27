package by.kosolobov.barbershop.controller.command.impl;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.dao.impl.UserDaoImpl;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.kosolobov.barbershop.util.AttrNames.*;

public class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private static final String LOGIN_PAGE = "jsp/login.jsp";
    private static final String PROFILE_PAGE = "jsp/client/person.jsp";//todo make constants
    private static final String ERROR = "error";
    private static final String USERNAME_ERROR = "username_error";
    private static final String PASSWORD_ERROR = "password_error";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        try {
            if (!UserServiceImpl.getInstance().checkUser(username, password)) {
                req.setAttribute(ERROR, PASSWORD_ERROR);
                return LOGIN_PAGE;
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            req.setAttribute(ERROR, USERNAME_ERROR);
            return LOGIN_PAGE;
        }

        //todo remove dao injection from commands
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = UserDaoImpl.getInstance().selectUserByUsername(username);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
        }

        userOptional.ifPresent(user -> {
            req.getSession().setAttribute(USERNAME, user.getUsername());
            req.getSession().setAttribute(USER_ROLE, user.getUserRole());
            req.getSession().setAttribute(FIRST_NAME, user.getFirstName());
            req.getSession().setAttribute(SECOND_NAME, user.getSecondName());
            req.getSession().setAttribute(SURNAME, user.getSurname());
            req.getSession().setAttribute(EMAIL, user.getEmail());
            req.getSession().setAttribute(PHONE, user.getPhone());
            req.getSession().setAttribute(DESCRIPTION, user.getDescription());
        });

        return PROFILE_PAGE;
    }
}
