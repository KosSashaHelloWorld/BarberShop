package by.kosolobov.barbershop.controller.command.impl.navigation;

import by.kosolobov.barbershop.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginMenuCommand implements Command {
    private static final String LOGIN_PAGE = "jsp/login.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return LOGIN_PAGE;
    }
}
