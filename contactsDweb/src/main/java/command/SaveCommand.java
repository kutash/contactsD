package command;


import utils.Builder;
import utils.BuilderFactory;
import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SaveCommand implements Command {

    private Logger logger = LogManager.getLogger(SaveCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();
    private Builder builder = BuilderFactory.getBuilder();
    private HttpServletRequest request;
    private HttpSession session;
    private Properties properties = new Properties();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("saving contact");
        this.request = request;
        session= request.getSession();
        session.removeAttribute("isSearch");
        Contact contact = builder.makeContact(request);
        Map<String, String> map = builder.validateContact(contact);
        if (map.size()!=0){
            request.setAttribute("contacts", contact);
            request.setAttribute("validations", map);
            return "/save.jspx";
        }else {
            Long id = contactService.setContact(contact);
            contact.setId(id);
            contactService.saveAddress(contact);
            savePhones(id);
            try {
                savePhoto(id);
                saveAttaches(id);
            } catch (Exception e) {
                throw new CommandException("Exception while saving contact", e);
            }
            return "/my-servlet?command=show";
        }
    }

    private void savePhoto(long id) throws IOException, ServletException {

        logger.info("saving photo");
        properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        String photoPath = properties.getProperty("AVATARS_PATH")+File.separator+id;
        File fileSaveDir;
        Part photoPart = request.getPart("photo");
        if (photoPart.getSize()>0){
            fileSaveDir = new File(photoPath);
            if (!fileSaveDir.exists()) {
                boolean created = fileSaveDir.mkdirs();
                if (!created){
                    logger.debug("Directory wasn't created");
                }
            }
            deleteAllFilesFolder(photoPath);
            String fileName = "";
            String contentDisp = photoPart.getHeader("Content-Disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }
            fileName = fileName.substring(fileName.indexOf("."));
            photoPath += File.separator + id + fileName;
            photoPart.write(photoPath);
            contactService.setPhoto(id, photoPath);
        }else {
            String pathTemp = (String)session.getAttribute("temp_photo_path");
            if (pathTemp != null){
                fileSaveDir = new File(photoPath);
                if (!fileSaveDir.exists()) {
                    boolean created = fileSaveDir.mkdirs();
                    if (!created){
                        logger.debug("Directory wasn't created");
                    }
                }
                deleteAllFilesFolder(photoPath);
                session.removeAttribute("temp_photo_path");
                File file = new File(pathTemp);
                FileUtils.moveFileToDirectory(file,fileSaveDir,true);
                photoPath += File.separator + file.getName();
                contactService.setPhoto(id, photoPath);
            }else {
                String path = contactService.getPhoto(id);
                if(path == null) contactService.setPhoto(id,null);
            }
        }
    }

    private void deleteAllFilesFolder(String path) {
        File[] files = new File(path).listFiles();
        if (files != null) {
            for (File myFile : files) {
                if (myFile.isFile()) {
                    boolean deleted = myFile.delete();
                    if (!deleted){
                        logger.debug("File wasn't deleted");
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void saveAttaches(long id) throws IOException {

        logger.info("saving attachments");
        List<Attachment> attaches;
        try {
            attaches = (List<Attachment>) session.getAttribute("attaches");
        }catch (ClassCastException e){
            throw new CommandException("Exception in casting types", e);
        }
        if (attaches != null) {
            properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));
            String savePath = properties.getProperty("ATTACH_PATH") + File.separator + id;
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                boolean created = saveDir.mkdirs();
                if (!created){
                    logger.debug("Directory wasn't created");
                }
            }
            /*List<String> fileNames = new ArrayList<>();
            for (Attachment attach : attaches) {
                fileNames.add(attach.getAttachName());
            }*/
            String path = properties.getProperty("TEMP_DIR");
            File tempDir = new File(path);
            String[] tempFiles = tempDir.list();
            if (tempFiles != null) {
                for (String tempFile : tempFiles) {
                    File file = new File(tempDir, tempFile);
                    FileUtils.moveFileToDirectory(file, saveDir, true);
                }
            }
            List<Attachment> finalList = new ArrayList<>();
            for (Attachment attachment : attaches) {
                if (attachment.getAttachId() == null) {
                    attachment.setAttachPath(savePath + File.separator + attachment.getAttachName());
                    attachment.setContactId(id);
                    finalList.add(attachment);
                } else {
                    finalList.add(attachment);
                }
            }
            String idContact = String.valueOf(id);
            contactService.setAttaches(idContact, finalList);
        }
    }

    private void savePhones(Long id){

        logger.info("saving phones");
        List<Phone> listPhones = builder.makePhone(request, id);
        String idContact = String.valueOf(id);
        contactService.setPhone(idContact, listPhones);
    }
}
