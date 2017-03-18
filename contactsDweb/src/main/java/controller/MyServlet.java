package controller;



import command.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;





/**
 * Created by Galina on 13.03.2017.
 */
@WebServlet(value = "/m")
public class MyServlet extends HttpServlet {

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
            } else {

            }
        } catch (Exception e) {
            //logger.error("Error while process request", e);
            //response.sendRedirect(request.getContextPath() + "/error.jsp");
        }


    }
}
