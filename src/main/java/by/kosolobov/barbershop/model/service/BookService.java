package by.kosolobov.barbershop.model.service;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.dao.BookDao;
import by.kosolobov.barbershop.model.entity.Book;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookService {
    private static final Logger log = LogManager.getLogger(BookService.class);
    private static final BookService instance = new BookService();

    private BookService() {}

    public static BookService getInstance() {
        return instance;
    }

    public List<Book> getClientBooksById(String id) throws ServiceException {
        try {
            return BookDao.getInstance().selectBookByClientId(id);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while selecting clients' books", e);
        }
    }
}
