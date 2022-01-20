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
        User.UserBuilder builder = new User.UserBuilder();

        for (Map<String, String> entity : data) {
            builder.setUserId(Integer.parseInt(entity.get(COLUMN_USER_ID)));
            builder.setUsername(entity.get(COLUMN_USERNAME));
            builder.setUserRole(User.Role.valueOf(entity.get(COLUMN_USER_ROLE).toUpperCase(Locale.ROOT)));
            builder.setFirstName(entity.get(COLUMN_FIRST_NAME));
            builder.setSecondName(entity.get(COLUMN_SECOND_NAME));
            builder.setSurName(entity.get(COLUMN_SUR_NAME));
            builder.setEmail(entity.get(COLUMN_EMAIL));
            builder.setPhone(entity.get(COLUMN_PHONE));
            builder.setDescription(entity.get(COLUMN_DESC));

            users.add(builder.build());
        }

        return users;
    }

    public static List<Service> mapService(List<Map<String, String>> data) {
        List<Service> services = new ArrayList<>();

        for (Map<String, String> entity : data) {
            services.add(new Service(Integer.parseInt(entity.get(COLUMN_SERVICE_ID)), entity.get(COLUMN_SERVICE_NAME)));
        }

        return services;
    }

    public static List<Service> mapServiceFull(List<Map<String, String>> data) {
        List<Service> services = new ArrayList<>();

        for (Map<String, String> entity : data) {
            services.add(new Service(
                    Integer.parseInt(entity.get(COLUMN_SERVICE_ID)),
                    entity.get(COLUMN_SERVICE_NAME),
                    Integer.parseInt(entity.get(COLUMN_BARBER_ID)),
                    Integer.parseInt(entity.get(COLUMN_PRICE))));
        }

        return services;
    }

    public static List<Book> mapBook(List<Map<String, String>> data) {
        List<Book> books = new ArrayList<>();
        Book.BookBuilder builder = new Book.BookBuilder();

        for (Map<String, String> entity : data) {
            builder.setClientId(Integer.parseInt(entity.get(COLUMN_CLIENT_ID)));
            builder.setBarberId(Integer.parseInt(entity.get(COLUMN_BARBER_ID)));
            builder.setServiceId(Integer.parseInt(entity.get(COLUMN_SERVICE_ID)));
            builder.setBookDate(Date.valueOf(entity.get(COLUMN_BOOK_DATE)));
            builder.setBookTime(Time.valueOf(entity.get(COLUMN_BOOK_TIME)));
            builder.setActive(Boolean.parseBoolean(entity.get(COLUMN_BOOK_ACTIVE)));

            books.add(builder.build());
        }

        return books;
    }
}
