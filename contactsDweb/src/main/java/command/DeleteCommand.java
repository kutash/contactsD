package command;

import model.Attachment;
import model.Contact;
import service.ContactService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Galina on 14.03.2017.
 */
public class DeleteCommand implements Command {

    private ContactService contactService = ServiceFactory.getContactService();
    private Logger logger = LogManager.getLogger(DeleteCommand.class);
    Properties properties = new Properties();


    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().removeAttribute("isSearch");
        String [] chosen = request.getParameterValues("idContact");
        logger.info("deleting contacts with id {}", Arrays.toString(chosen));
        for(String c : chosen) {
            long id = Long.parseLong(c);
            Contact contact = contactService.getById(id);
            Long idAddress = contact.getAddress().getAddressId();

            try {

                deletePhoto(id);
                deleteAttaches(id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            contactService.deletePhones(id);
            contactService.deleteContact(id);
            contactService.deleteAddress(idAddress);
        }

        return "/my-servlet?command=show";
    }


    private void deletePhoto(long idContact) throws IOException {
        logger.info("deletiog photo contact id {}", idContact);
        String path = contactService.getPhoto(idContact);
        if (path != null){
            File file = new File(path);
            if (file.canWrite() && file.exists()) {
                System.out.println(file.delete());
            }
        }
        properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        String dirPath = properties.getProperty("AVATARS_PATH")+File.separator+idContact;
        File file = new File(dirPath);
        if (file.isDirectory() && file.exists() && (file.list().length == 0)){
            file.delete();
        }

    }


    private void deleteAttaches(Long idContact) throws IOException {
        logger.info("deletiog attachments contact id {}", idContact);
        List<Attachment> attachments = contactService.getAttaches(idContact);
        for (Attachment attachment : attachments) {
            if (attachment != null) {
                String attachPath = attachment.getAttachPath();
                if (attachPath != null) {
                    File file = new File(attachPath);
                    if (file.canWrite() && file.exists()) {
                        file.delete();
                    }
                }
            }
        }
            properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));
            String dirPath = properties.getProperty("ATTACH_PATH")+File.separator+idContact;
            File file = new File(dirPath);
            if (file.isDirectory() && (file.list().length == 0)){
                file.delete();
            }
            contactService.deleteAttachment(idContact);

    }
}
