package by.kosolobov.barbershop.controller.command.impl;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.kosolobov.barbershop.util.AttrNames.*;

public class SignupCommand implements Command {
    private static final String PROFILE_PAGE = "jsp/client/person.jsp";
    private static final String SIGNUP_PAGE = "jsp/signup.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService service = UserService.getInstance();

        String username = req.getParameter(USERNAME);
        if (!service.uniqueUsername(username)) {
            req.setAttribute("username_error", "not_unique");
            return SIGNUP_PAGE;
        }
        String password = req.getParameter(PASSWORD);
        String role = req.getParameter(USER_ROLE);

        service.signupUser(username, password, role);

        String firstName = req.getParameter(FIRST_NAME);
        String secondName = req.getParameter(SECOND_NAME);
        String surname = req.getParameter(SUR_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);

        service.updateUser(username, firstName, secondName, surname, email, phone);

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
