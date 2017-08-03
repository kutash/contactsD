package controller;

import command.*;
import mailing.BirthdayMailing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/m")
@MultipartConfig(fileSizeThreshold=1024*1024*2,
        maxFileSize=1024*1024*10,
        maxRequestSize=1024*1024*50)
public class MyServlet extends HttpServlet {

    private Logger logger = LogManager.getLogger(MyServlet.class);
    private BirthdayMailing  birthdayMailing = new BirthdayMailing();

    public void init() throws ServletException{
        try {
            birthdayMailing.startService();
        } catch (Exception e) {
            logger.error("Error in init method", e);
        }

    }

    @Override
    public void destroy() {
        try {
            birthdayMailing.stopService();
        } catch (Exception e) {
            logger.error("Error in destroy method", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            CommandFactory client = new CommandFactory();
            Command command = client.defineCommand(request);
            String page = command.execute(request,response);
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error in processRequest method", e);
        }


    }
}
