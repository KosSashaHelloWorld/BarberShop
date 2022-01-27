package by.kosolobov.barbershop.controller.command.impl;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.service.UserService;
import by.kosolobov.barbershop.model.service.impl.UserServiceImpl;
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
    private static final String USERNAME_ERROR = "username_error";
    private static final String NOT_UNIQUE = "not_unique";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService service = UserServiceImpl.getInstance();

        //checking same user already exist
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
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

        //getting other users' attributes
        User user = new User();
        user.setUsername(username);
        user.setUserRole(User.Role.valueOf(req.getParameter(USER_ROLE)));
        user.setFirstName(req.getParameter(FIRST_NAME));
        user.setSecondName(req.getParameter(SECOND_NAME));
        user.setSurname(req.getParameter(SURNAME));
        user.setEmail(req.getParameter(EMAIL));
        user.setPhone(req.getParameter(PHONE));
        user.setDescription(req.getParameter(DESCRIPTION));

        //adding user to database
        try {
            service.signupUser(user, password);
        } catch (ServiceException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            req.setAttribute(SERVICE_ERROR, SIGNUP_NOT_COMPLETE);
            return SIGNUP_PAGE;
        }

        //adding users' attributes to users' session
        req.getSession().setAttribute(USERNAME, user.getUsername());
        req.getSession().setAttribute(USER_ROLE, user.getUserRole());
        req.getSession().setAttribute(FIRST_NAME, user.getFirstName());
        req.getSession().setAttribute(SECOND_NAME, user.getSecondName());
        req.getSession().setAttribute(SURNAME, user.getSurname());
        req.getSession().setAttribute(EMAIL, user.getEmail());
        req.getSession().setAttribute(PHONE, user.getPhone());
        req.getSession().setAttribute(DESCRIPTION, user.getDescription());

        return PROFILE_PAGE;
    }
}
