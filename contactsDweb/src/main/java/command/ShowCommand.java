package command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.*;
import model.Contact;


import java.sql.SQLException;
import java.util.List;



/**
 * Created by Galina on 14.03.2017.
 */
public class ShowCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response)  {
        try {

            DAO contactDAO = DAOFactory.getContactDao();
            int contactsCount = contactDAO.getContactsCount();
            int targetPage = 2;
            int pagesCount = (int) Math.ceil(contactsCount / 10.0);
            List<Contact> contacts = contactDAO.getCont(targetPage);


        request.setAttribute("contact", contacts);

        }catch (SQLException e){
            e.printStackTrace();
        }

        return "/show.jspx";
    }

}
