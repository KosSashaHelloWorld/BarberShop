package by.kosolobov.barbershop.controller.command.impl.barber;

import by.kosolobov.barbershop.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowBarberBooksCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return null;//todo
    }
}
