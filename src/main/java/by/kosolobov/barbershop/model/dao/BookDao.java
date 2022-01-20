package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.entity.Book;
import by.kosolobov.barbershop.model.mapper.EntityMapper;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static by.kosolobov.barbershop.model.dao.DatabaseNameBox.*;

public class BookDao {
    private static final ReentrantLock lock = new ReentrantLock();
    private static BookDao instance;
    private final DaoBuilder dao = new DaoBuilder();

    public static BookDao getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new BookDao();
            }
            lock.unlock();
        }
        return instance;
    }

    private BookDao() {
    }

    public List<Book> selectBookByClientId(String id) throws DaoException {
        return selectBookByUserId(COLUMN_CLIENT_ID, id);
    }

    public List<Book> selectBookByBarberId(String id) throws DaoException {
        return selectBookByUserId(DatabaseNameBox.COLUMN_BARBER_ID, id);
    }

    public List<Book> selectBookByServiceId(String id) throws DaoException {
        return selectBookByUserId(DatabaseNameBox.COLUMN_SERVICE_ID, id);
    }


    public List<Book> selectBookByUserId(String column, String id) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(column, id)
                .executeSql(COLUMNS_BOOK));
    }

    public List<Book> selectBookByDate(Date date) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_BOOK_DATE, date.toString())
                .executeSql(COLUMNS_BOOK));
    }

    public List<Book> selectBookByTime(Time time) throws DaoException {
        return EntityMapper.mapBook(dao.select(TABLE_BOOK, COLUMNS_BOOK)
                .where(COLUMN_BOOK_TIME, time.toString())
                .executeSql(COLUMNS_BOOK));
    }

    public boolean updateBookDateTime(Date oldDate, Time oldTime, Date newDate, Time newTime) throws DaoException {
        return dao.update(TABLE_BOOK)
                .set(COLUMN_BOOK_DATE, newDate.toString())
                .andSet(COLUMN_BOOK_TIME, newTime.toString())
                .where(COLUMN_BOOK_DATE, oldDate.toString())
                .andWhere(COLUMN_BOOK_TIME, oldTime.toString())
                .execute();
    }

    public boolean updateBookStatus(Date date, Time time, boolean active) throws DaoException {
        return dao.update(TABLE_BOOK)
                .set(COLUMN_BOOK_ACTIVE, String.valueOf(active))
                .where(COLUMN_BOOK_DATE, date.toString())
                .andWhere(COLUMN_BOOK_TIME, time.toString())
                .execute();
    }

    public boolean insertBook(int client, int barber, int service, Date date, Time time) throws DaoException {
        return dao.insert(TABLE_BOOK, COLUMNS_BOOK)
                .values(String.valueOf(client), String.valueOf(barber),
                        String.valueOf(service), date.toString(), time.toString(), "true")
                .execute();
    }

    public boolean deleteBook(Date date, Time time) throws DaoException {
        return dao.delete(TABLE_BOOK)
                .where(COLUMN_BOOK_DATE, date.toString())
                .andWhere(COLUMN_BOOK_TIME, time.toString())
                .execute();
    }

    public boolean deleteAllInactive() throws DaoException {
        return dao.delete(TABLE_BOOK)
                .where(COLUMN_BOOK_ACTIVE, "false")
                .execute();
    }
}
