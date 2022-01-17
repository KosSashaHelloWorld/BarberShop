package by.kosolobov.barbershop.controller.command.impl.client;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.model.dao.BookDao;
import by.kosolobov.barbershop.model.entity.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


public class ShowClientBooksCommand implements Command {
    private static final String USER_ID = "user_id";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = String.valueOf(req.getSession().getAttribute(USER_ID));
        List<Book> books = BookDao.getInstance().selectBookByClientId(id);
        req.setAttribute("items", books);
        return "person.jsp";
    }
}
