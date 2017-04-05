package command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.*;
import model.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;


import java.sql.SQLException;
import java.util.List;



/**
 * Created by Galina on 14.03.2017.
 */
public class ShowCommand implements Command {

    private Logger logger = LogManager.getLogger(PhotoCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();
    HttpSession session;

    public String execute(HttpServletRequest request, HttpServletResponse response)  {
        logger.info("showing contacts");
        session = request.getSession();
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");

        String s = request.getParameter("currentPage");
        int targetPage = 0;
        if (s!=null){
           targetPage = Integer.parseInt(s);

        }else {
            targetPage = 1;
        }
        int contactsCount = contactService.getContactsCount();
        int pagesCount = (int) Math.ceil(contactsCount / 10.0);
        List<Contact> contacts = contactService.getCont(targetPage);
        request.setAttribute("contact", contacts);
        request.setAttribute("pages", pagesCount);
        request.setAttribute("currentPage", targetPage);

        return "/show.jspx";
    }
}
