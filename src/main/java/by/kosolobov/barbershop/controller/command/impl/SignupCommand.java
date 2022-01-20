package by.kosolobov.barbershop.controller.command.impl;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.kosolobov.barbershop.util.AttrNames.*;

public class SignupCommand implements Command {
    private static final Logger log = LogManager.getLogger(SignupCommand.class);
    private static final String PROFILE_PAGE = "jsp/client/person.jsp";
    private static final String SIGNUP_PAGE = "jsp/signup.jsp";
    private static final String SERVICE_ERROR = "service_error";
    private static final String USERNAME_NOT_VALID = "username_not_valid";
    private static final String SIGNUP_NOT_COMPLETE = "signup_not_complete";
    private static final String UPDATE_NOT_COMPLETE = "update_not_complete";
    private static final String USERNAME_ERROR = "username_error";
    private static final String NOT_UNIQUE = "not_unique";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService service = UserService.getInstance();

        String username = req.getParameter(USERNAME);
        try {
            if (!service.uniqueUsername(username)) {
                req.setAttribute(USERNAME_ERROR, NOT_UNIQUE);
                return SIGNUP_PAGE;
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "Service exception happened while checking username: {}", e.getMessage(), e);
            req.setAttribute(SERVICE_ERROR, USERNAME_NOT_VALID);
            return SIGNUP_PAGE;
        }
        String password = req.getParameter(PASSWORD);
        String role = req.getParameter(USER_ROLE);

        try {
            service.signupUser(username, password, role);
        } catch (ServiceException e) {
            log.log(Level.ERROR, "Service exception happened while signing up user: {}", e.getMessage(), e);
            req.setAttribute(SERVICE_ERROR, SIGNUP_NOT_COMPLETE);
            return SIGNUP_PAGE;
        }

        String firstName = req.getParameter(FIRST_NAME);
        String secondName = req.getParameter(SECOND_NAME);
        String surname = req.getParameter(SUR_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);

        try {
            service.updateUser(username, firstName, secondName, surname, email, phone);
        } catch (ServiceException e) {
            log.log(Level.ERROR, "Service exception happened while updating user: {}", e.getMessage(), e);
            req.setAttribute(SERVICE_ERROR, UPDATE_NOT_COMPLETE);
            return SIGNUP_PAGE;
        }

        req.getSession().setAttribute(USERNAME, username);
        req.getSession().setAttribute(USER_ROLE, role);
        req.getSession().setAttribute(FIRST_NAME, firstName);
        req.getSession().setAttribute(SECOND_NAME, secondName);
        req.getSession().setAttribute(SUR_NAME, surname);
        req.getSession().setAttribute(EMAIL, email);
        req.getSession().setAttribute(PHONE, phone);

        return PROFILE_PAGE;
    }
}
