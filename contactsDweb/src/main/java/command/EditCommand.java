package command;

import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditCommand implements Command {

    private Logger logger = LogManager.getLogger(EditCommand.class);
    private ContactService contactService = ContactServiceFactory.getContactService();
    private AttachmentService attachmentService = AttachmentServiceFactory.getAttachmentService();
    private PhoneService phoneService = PhoneServiceFactory.getPhoneService();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");
        session.removeAttribute("isSearch");
        Long contactId = Long.parseLong(request.getParameter("idContact"));
        Contact contact = contactService.getById(contactId);
        List<Attachment> attachments = attachmentService.getAttaches(contactId);
        List<Phone> listPhones = phoneService.getPhones(contactId);
        request.setAttribute("phones", listPhones);
        request.setAttribute("attaches",attachments);
        request.setAttribute("contacts", contact);
        logger.info("edit contact id {}", contactId);
        return "/save.jspx";
    }
}
