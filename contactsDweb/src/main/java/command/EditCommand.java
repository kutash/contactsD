package command;

import DAO.*;
import model.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Galina on 17.03.2017.
 */
public class EditCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        DAO contactDAO = DAOFactory.getContactDao();
        Long contactId = Long.parseLong(request.getParameter("idContact"));

        Contact contact = contactDAO.getById(contactId);

        request.setAttribute("contact", contact);

        return "/save.jspx";
    }
}
