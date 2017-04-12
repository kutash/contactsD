package command;

import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Galina on 08.04.2017.
 */
public class CancelCommand implements Command {

    private Logger logger = LogManager.getLogger(CancelCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();
    HttpSession session;
    private Properties properties = new Properties();

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("cancel saving");
        session = request.getSession();
        try {
            properties.load(CancelCommand.class.getResourceAsStream("/attachment.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String attachPath = properties.getProperty("TEMP_DIR");
        String photoPath = properties.getProperty("TEMP_PHOTO_DIR");

        if (StringUtils.isNotEmpty(request.getParameter("idContact"))){
            session.removeAttribute("attaches");
            session.removeAttribute("temp_photo_path");
            session.removeAttribute("isSearch");
            deleteAllFilesFolder(attachPath);
            deleteAllFilesFolder(photoPath);
            Long contactId = Long.parseLong(request.getParameter("idContact"));
            Contact contact = contactService.getById(contactId);
            List<Attachment> attachments = contactService.getAttaches(contactId);
            List<Phone> listPhones = contactService.getPhones(contactId);
            request.setAttribute("phones", listPhones);
            request.setAttribute("attaches",attachments);
            request.setAttribute("contacts", contact);
            return "/save.jspx";
        } else {
            deleteAllFilesFolder(attachPath);
            deleteAllFilesFolder(photoPath);
            session.removeAttribute("attaches");
            session.removeAttribute("temp_photo_path");
            session.removeAttribute("isSearch");
            return "/save.jspx";
        }
    }


    public static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles())
            if (myFile.isFile()) myFile.delete();
    }
}
