package by.kosolobov.barbershop.controller.command.impl;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.model.dao.UserDao;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.util.Cryptographer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class LoginCommand implements Command {
    private static final String LOGIN_PAGE = "jsp/login.jsp";
    private static final String PROFILE_PAGE = "jsp/client/person.jsp";
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "user_role";
    private static final String FIRST_NAME = "first_name";
    private static final String SECOND_NAME = "second_name";
    private static final String SUR_NAME = "sur_name";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PHONE = "user_phone";
    private static final String DESCRIPTION = "user_desc";
    private static final String ERROR = "error";
    private static final String USERNAME_ERROR = "username_error";
    private static final String PASSWORD_ERROR = "password_error";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        Optional<String> savedPassword = UserDao.getInstance().selectPassword(username);

        if (savedPassword.isPresent()) {
            if (!Cryptographer.check(password, savedPassword.get())) {
                req.setAttribute(ERROR, PASSWORD_ERROR);
                return LOGIN_PAGE;
            }
        } else {
            req.setAttribute(ERROR, USERNAME_ERROR);
            return LOGIN_PAGE;
        }

        Optional<User> userOptional = UserDao.getInstance().selectUserByUsername(username);

        userOptional.ifPresent(user -> {
            req.getSession().setAttribute(USER_ID, user.getUserId());
            req.getSession().setAttribute(USERNAME, user.getUsername());
            req.getSession().setAttribute(ROLE, user.getUserRole());
            req.getSession().setAttribute(FIRST_NAME, user.getFirstName());
            req.getSession().setAttribute(SECOND_NAME, user.getSecondName());
            req.getSession().setAttribute(SUR_NAME, user.getSurName());
            req.getSession().setAttribute(USER_EMAIL, user.getEmail());
            req.getSession().setAttribute(USER_PHONE, user.getPhone());
            req.getSession().setAttribute(DESCRIPTION, user.getDescription());
        });

        return PROFILE_PAGE;
    }
}
