package command;


import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Galina on 17.03.2017.
 */
public class EditCommand implements Command {

    private Logger logger = LogManager.getLogger(EditCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("attaches");
        Long contactId = Long.parseLong(request.getParameter("idContact"));
        Contact contact = contactService.getById(contactId);
        List<Attachment> attachments = contactService.getAttaches(contactId);
        List<Phone> listPhones = contactService.getPhones(contactId);
        request.setAttribute("phones", listPhones);
        request.setAttribute("attaches",attachments);
        request.setAttribute("contact", contact);
        logger.info("edit contact id {}", contactId);
        return "/save.jspx";
    }
}
