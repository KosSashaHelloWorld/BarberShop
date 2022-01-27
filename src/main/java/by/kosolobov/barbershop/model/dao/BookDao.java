package by.kosolobov.barbershop.model.dao;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.entity.Book;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    boolean deleteBook(Date date, Time time, String barberUsername) throws DaoException;
    boolean insertBook(Book book) throws DaoException;
    Optional<Book> selectBook(Date date, Time time, String barberUsername) throws DaoException;
    List<Book> selectBookByBarber(String barberUsername) throws DaoException;
    List<Book> selectBookByClient(String clientUsername) throws DaoException;
    List<Book> selectBookByService(String serviceName) throws DaoException;
    List<Book> selectBookByDate(Date date) throws DaoException;
    List<Book> selectBookByTime(Time time) throws DaoException;
    boolean updateBookState(Date date, Time time, String barberUsername, Book.BookState state) throws DaoException;
}
