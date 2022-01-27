package by.kosolobov.barbershop.model.dao.impl;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.dao.BookDao;
import by.kosolobov.barbershop.model.dao.DaoBuilder;
import by.kosolobov.barbershop.model.entity.Book;
import by.kosolobov.barbershop.model.mapper.EntityMapper;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.dao.DatabaseNameBox.*;

public class BookDaoImpl implements BookDao {
    private static final ReentrantLock lock = new ReentrantLock();
    private static BookDaoImpl instance;
    private final DaoBuilder dao = new DaoBuilder();

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new BookDaoImpl();
            }
            lock.unlock();
        }
        return instance;
    }

    @Override
    public boolean deleteBook(Date date, Time time, String barberUsername) throws DaoException {
        return dao.delete(TABLE_BOOK)
                .where(COLUMN_BOOK_DATE, date.toString())
                .andWhere(COLUMN_BOOK_TIME, time.toString())
                .execute();
    }

    @Override
    public boolean insertBook(Book book) throws DaoException {
        return dao.insert(TABLE_BOOK, COLUMNS_BOOK)
                .values(book.getClient(), book.getBarber(), book.getService(),
                        book.getDate().toString(), book.getTime().toString(),
                        book.getState().toString())
                .execute();
    }

    @Override
    public Optional<Book> selectBook(Date date, Time time, String barberUsername) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Book> selectBookByBarber(String barberUsername) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_BARBER_USERNAME, barberUsername)
                .executeSql(COLUMNS_BOOK));
    }

    @Override
    public List<Book> selectBookByClient(String clientUsername) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_CLIENT_USERNAME, clientUsername)
                .executeSql(COLUMNS_BOOK));
    }

    @Override
    public List<Book> selectBookByService(String serviceName) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_SERVICE_NAME, serviceName)
                .executeSql(COLUMNS_BOOK));
    }

    @Override
    public List<Book> selectBookByDate(Date date) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_BOOK_DATE, date.toString())
                .executeSql(COLUMNS_BOOK));
    }

    @Override
    public List<Book> selectBookByTime(Time time) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_BOOK_TIME, time.toString())
                .executeSql(COLUMNS_BOOK));
    }

    @Override
    public boolean updateBookState(Date date, Time time, String barberUsername, Book.BookState state) throws DaoException {
        return dao.update(TABLE_BOOK)
                .set(COLUMN_BOOK_STATE, state.toString())
                .where(COLUMN_BOOK_DATE, date.toString())
                .andWhere(COLUMN_BOOK_TIME, time.toString())
                .execute();
    }
}
