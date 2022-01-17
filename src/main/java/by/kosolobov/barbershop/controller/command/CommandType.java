package by.kosolobov.barbershop.controller.command;

import by.kosolobov.barbershop.controller.command.impl.IllegalCommand;
import by.kosolobov.barbershop.controller.command.impl.LoginCommand;
import by.kosolobov.barbershop.controller.command.impl.LogoutCommand;
import by.kosolobov.barbershop.controller.command.impl.SignupCommand;
import by.kosolobov.barbershop.controller.command.impl.barber.ShowBarberBooksCommand;
import by.kosolobov.barbershop.controller.command.impl.client.DeleteUserCommand;
import by.kosolobov.barbershop.controller.command.impl.client.ShowClientBooksCommand;
import by.kosolobov.barbershop.controller.command.impl.navigation.LoginMenuCommand;
import by.kosolobov.barbershop.controller.command.impl.navigation.SignupMenuCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    DELETE_USER(new DeleteUserCommand()),
    SHOW_CLIENT_BOOK(new ShowClientBooksCommand()),
    SHOW_BARBER_BOOK(new ShowBarberBooksCommand()),
    ILLEGAL_COMMAND(new IllegalCommand()),
    LOGOUT(new LogoutCommand()),
    LOGIN(new LoginCommand()),
    SIGNUP(new SignupCommand()),
    LOGIN_MENU(new LoginMenuCommand()),
    SIGNUP_MENU(new SignupMenuCommand());

    private static final Logger log = LogManager.getLogger(CommandType.class);
    private static final String DEFAULT_ERROR_COMMAND = "ILLEGAL_NAME";
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String strCommand) {
        CommandType type;
        try {
            type = CommandType.valueOf(strCommand);
        } catch (IllegalArgumentException e) {
            log.log(Level.ERROR, "Illegal command type! Redirecting to main menu! Error message: {}", e.getMessage(), e);
            type = CommandType.valueOf(DEFAULT_ERROR_COMMAND);
        }

        return type.getCommand();
    }

    public Command getCommand() {
        return command;
    }
}
