package by.kosolobov.barbershop.controller.command.impl.client;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.entity.Book;
import by.kosolobov.barbershop.model.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static by.kosolobov.barbershop.util.AttrNames.USERNAME;


public class ShowClientBooksCommand implements Command {
    private static final Logger log = LogManager.getLogger(ShowClientBooksCommand.class);
    private static final String SERVICE_ERROR = "service_error";
    private static final String SELECT_BOOK_NOT_COMPLETE = "select_book_not_complete";
    private static final String PROFILE_PAGE = "jsp/client/person.jsp";
    private static final String BOOKS = "books";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String clientUsername = String.valueOf(req.getSession().getAttribute(USERNAME));
        List<Book> books = new ArrayList<>();
        try {
            books = BookServiceImpl.getInstance().getClientBooks(clientUsername);
        } catch (ServiceException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            req.setAttribute(SERVICE_ERROR, SELECT_BOOK_NOT_COMPLETE);
        }
        req.setAttribute(BOOKS, books);
        return PROFILE_PAGE;
    }
}
