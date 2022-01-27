package by.kosolobov.barbershop.model.service;

import by.kosolobov.barbershop.exception.ServiceException;
import by.kosolobov.barbershop.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getClientBooks(String clientUsername) throws ServiceException;
    List<Book> getBarberBooks(String barberUsername) throws ServiceException;
}
