package by.kosolobov.barbershop.model.mapper;

import by.kosolobov.barbershop.model.entity.Book;
import by.kosolobov.barbershop.model.entity.Service;
import by.kosolobov.barbershop.model.entity.User;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static by.kosolobov.barbershop.model.dao.DatabaseNameBox.*;

public class EntityMapper {
    public static List<User> mapUser(List<Map<String, String>> data) {
        List<User> users = new ArrayList<>();

        for (Map<String, String> entity : data) {
            users.add(mapUser(entity));
        }

        return users;
    }

    public static User mapUser(Map<String, String> entity) {
        User user = new User();
        user.setUsername(entity.get(COLUMN_USERNAME));
        user.setUserRole(User.Role.valueOf(entity.get(COLUMN_USER_ROLE).toUpperCase(Locale.ROOT)));
        user.setFirstName(entity.get(COLUMN_FIRST_NAME));
        user.setSecondName(entity.get(COLUMN_SECOND_NAME));
        user.setSurname(entity.get(COLUMN_SURNAME));
        user.setEmail(entity.get(COLUMN_EMAIL));
        user.setPhone(entity.get(COLUMN_PHONE));
        user.setDescription(entity.get(COLUMN_DESC));
        return user;
    }

    public static List<Service> mapService(List<Map<String, String>> data) {
        List<Service> services = new ArrayList<>();

        for (Map<String, String> entity : data) {
            services.add(mapService(entity));
        }

        return services;
    }

    public static Service mapService(Map<String, String> entity) {
        Service service = new Service();
        service.setBarberUsername(entity.get(COLUMN_BARBER_USERNAME));
        service.setServiceName(entity.get(COLUMN_SERVICE_NAME));
        service.setPrice(Double.parseDouble(entity.get(COLUMN_PRICE)));
        return service;
    }

    public static List<Book> mapBook(List<Map<String, String>> data) {
        List<Book> books = new ArrayList<>();
        for (Map<String, String> entity : data) {
            books.add(mapBook(entity));
        }
        return books;
    }

    public static Book mapBook(Map<String, String> data) {
        Book book = new Book();
        book.setClient(data.get(COLUMN_CLIENT_USERNAME));
        book.setBarber(data.get(COLUMN_BARBER_USERNAME));
        book.setService(data.get(COLUMN_SERVICE_NAME));
        book.setDate(Date.valueOf(data.get(COLUMN_BOOK_DATE)));
        book.setTime(Time.valueOf(data.get(COLUMN_BOOK_TIME)));
        book.setState(Book.BookState.valueOf(data.get(COLUMN_BOOK_STATE)));
        return book;
    }
}
