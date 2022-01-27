package by.kosolobov.barbershop.model.service.impl;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.dao.impl.BookDaoImpl;
import by.kosolobov.barbershop.model.entity.Book;
import by.kosolobov.barbershop.model.service.BookService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);
    private static final BookServiceImpl instance = new BookServiceImpl();

    private BookServiceImpl() {}

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Book> getClientBooks(String clientUsername) throws ServiceException {
        try {
            return BookDaoImpl.getInstance().selectBookByClient(clientUsername);
        } catch (DaoException e) {
            log.log(Level.ERROR, "ERROR: {}", e.getMessage(), e);
            throw new ServiceException("Dao exception happened while selecting clients' books", e);
        }
    }

    @Override
    public List<Book> getBarberBooks(String barberUsername) throws ServiceException {
        return null;
    }
}
