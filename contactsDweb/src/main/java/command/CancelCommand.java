package command;

import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

public class CancelCommand implements Command {

    private Logger logger = LogManager.getLogger(CancelCommand.class);
    private ContactService contactService = ContactServiceFactory.getContactService();
    private AttachmentService attachmentService = AttachmentServiceFactory.getAttachmentService();
    private PhoneService phoneService = PhoneServiceFactory.getPhoneService();

    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("reset saving");
        HttpSession session = request.getSession();
        if(session.getAttribute("temp_photo_path") != null && session.getAttribute("attaches") != null) {
            File photoPath = new File(session.getAttribute("temp_photo_path").toString());
            List<Attachment> attachments;
            File attachPath;
            try {
                attachments = (List<Attachment>) session.getAttribute("attaches");
            }catch (ClassCastException e){
                throw new CommandException("Exception while casting types", e);
            }
            if (photoPath.canWrite() && photoPath.exists()) {
                boolean deleted = photoPath.delete();
                if (!deleted) {
                    logger.debug("Temporary photo wasn't deleted");
                }
            }
            for (Attachment attachment : attachments) {
                attachPath = new File(attachment.getAttachPath());
                if (attachPath.canWrite() && attachPath.exists()) {
                    boolean deleted = attachPath.delete();
                    if (!deleted) {
                        logger.debug("Temporary attaches weren't deleted");
                    }
                }
            }
        }
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");
        session.removeAttribute("isSearch");
        if (StringUtils.isNotEmpty(request.getParameter("idContact"))) {
            Long contactId = Long.parseLong(request.getParameter("idContact"));
            Contact contact = contactService.getById(contactId);
            List<Attachment> attaches = attachmentService.getAttaches(contactId);
            List<Phone> listPhones = phoneService.getPhones(contactId);
            request.setAttribute("phones", listPhones);
            request.setAttribute("attaches", attaches);
            request.setAttribute("contacts", contact);
        }
        return "/save.jspx";
    }
}
