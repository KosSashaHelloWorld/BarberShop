package by.kosolobov.barbershop.controller.command.impl.client;

import by.kosolobov.barbershop.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateClientCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateClientCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return null;//todo
    }
}
