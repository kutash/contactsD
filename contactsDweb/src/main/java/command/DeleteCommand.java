package command;

import model.Attachment;
import service.ContactService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;

public class DeleteCommand implements Command {

    private ContactService contactService = ServiceFactory.getContactService();
    private Logger logger = LogManager.getLogger(DeleteCommand.class);
    private Properties properties = new Properties();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().removeAttribute("isSearch");
        String [] chosen = request.getParameterValues("idContact");
        String idContacts = Arrays.toString(chosen);
        idContacts = idContacts.substring(1,idContacts.length()-1);
        logger.info("deleting contacts with id {}", Arrays.toString(chosen));
        for(String c : chosen) {
            long id = Long.parseLong(c);
            try {
                deletePhoto(id);
                deleteAttaches(id);
            } catch (IOException e) {
                throw new CommandException("Exception in deleting photo or attaches", e);
            }
        }
        contactService.deleteAttachment(idContacts);
        contactService.deletePhones(idContacts);
        contactService.deleteAddress(idContacts);
        contactService.deleteContact(idContacts);
        return "/my-servlet?command=show";
    }

    private void deletePhoto(long idContact) throws IOException {

        logger.info("deleting photo contact id {}", idContact);
        String path = contactService.getPhoto(idContact);
        if (path != null){
            File file = new File(path);
            if (file.canWrite() && file.exists()) {
                boolean deleted = file.delete();
                if (!deleted){
                    logger.debug("Photo wasn't deleted");
                }
            }
        }
        properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        String dirPath = properties.getProperty("AVATARS_PATH")+File.separator+idContact;
        File file = new File(dirPath);
        deleteEmptyFolder(file);
    }

    private void deleteAttaches(Long idContact) throws IOException {

        logger.info("deleting attachments contact id {}", idContact);
        List<Attachment> attachments = contactService.getAttaches(idContact);
        for (Attachment attachment : attachments) {
            if (attachment != null) {
                String attachPath = attachment.getAttachPath();
                if (attachPath != null) {
                    File file = new File(attachPath);
                    if (file.canWrite() && file.exists()) {
                        boolean deleted = file.delete();
                        if (!deleted){
                            logger.debug("Attachment wasn't deleted");
                        }
                    }
                }
            }
        }
        properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));
        String dirPath = properties.getProperty("ATTACH_PATH")+File.separator+idContact;
        File file = new File(dirPath);
        deleteEmptyFolder(file);
    }

    private void deleteEmptyFolder(File file){
        String[] tempFiles = file.list();
        if (tempFiles != null && file.isDirectory() && file.exists()) {
            int length = tempFiles.length;
            if (length == 0) {
                boolean deleted = file.delete();
                if (!deleted) {
                    logger.debug("Empty directory wasn't deleted");
                }
            }
        }
    }
}
