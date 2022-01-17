package by.kosolobov.barbershop.controller;

import by.kosolobov.barbershop.controller.command.Command;
import by.kosolobov.barbershop.controller.command.CommandType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = "/controller")
public class FrontController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FrontController.class);
    private static final String COMMAND = "command";
    private static final String ERROR = "error";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strCommand = req.getParameter(COMMAND);
        Command command = CommandType.define(strCommand);
        String path = command.execute(req, resp);

        if (path != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            dispatcher.forward(req, resp);
        } else {
            log.log(Level.WARN, "Executing command \"{}\" failed!", strCommand);
            req.setAttribute(ERROR, strCommand);
        }
    }
}
