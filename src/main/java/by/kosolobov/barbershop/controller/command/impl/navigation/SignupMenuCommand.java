package by.kosolobov.barbershop.controller.command.impl.navigation;

import by.kosolobov.barbershop.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignupMenuCommand implements Command {
    private static final String SIGNUP_PAGE = "jsp/signup.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return SIGNUP_PAGE;
    }
}
