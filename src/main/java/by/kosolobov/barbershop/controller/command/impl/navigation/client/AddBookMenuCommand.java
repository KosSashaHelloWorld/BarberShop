package by.kosolobov.barbershop.controller.command.impl.navigation.client;

import by.kosolobov.barbershop.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddBookMenuCommand implements Command {
    private static final String ADD_BOOK_PAGE = "jsp/client/add_book.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ADD_BOOK_PAGE;
    }
}
