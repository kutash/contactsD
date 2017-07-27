package command;


import builder.Builder;
import builder.BuilderFactory;
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
import java.util.Properties;


/**
 * Created by Galina on 14.03.2017.
 */
public class SaveCommand implements Command {

    private Logger logger = LogManager.getLogger(SaveCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();
    private Builder builder = BuilderFactory.getBuilder();
    private HttpServletRequest request;
    private HttpSession session;


    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("saving contact");
        this.request = request;
        session= request.getSession();
        session.removeAttribute("isSearch");
        Contact contact = builder.makeContact(request);
        if (contact==null){
            return "/error.jspx";
        }else {
            Long id = contactService.setContact(contact);
            contact.setId(id);
            contactService.saveAddress(contact);
            savePhones(id);
            try {
                savePhoto(id);
                saveAttaches(id);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/my-servlet?command=show";
        }
    }

    private void savePhoto(long id) throws IOException, ServletException {
        logger.info("saving photo");
        Properties properties = new Properties();
        properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        String photoPath = properties.getProperty("AVATARS_PATH")+File.separator+id;
        File fileSaveDir = null;

        Part photoPart = request.getPart("photo");

        if (photoPart.getSize()>0){
            fileSaveDir = new File(photoPath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
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
                    fileSaveDir.mkdirs();
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


    public static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles())
            if (myFile.isFile()) myFile.delete();
    }

    private void saveAttaches(long id) throws IOException {
        logger.info("saving attachments");
        List<Attachment> attaches = (List<Attachment>) session.getAttribute("attaches");
        if (attaches != null) {
            Properties properties = new Properties();
            properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));

            String savePath = properties.getProperty("ATTACH_PATH") + File.separator + id;

            File saveDir = new File(savePath);

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            List<String> fileNames = new ArrayList<String>();
            for (Attachment attach : attaches) {
                fileNames.add(attach.getAttachName());
            }
            String path = properties.getProperty("TEMP_DIR");
            File tempDir = new File(path);
            String[] tempFiles = tempDir.list();
            for (int i = 0; i < tempFiles.length; i++) {
                File file = new File(tempDir, tempFiles[i]);

                FileUtils.moveFileToDirectory(file, saveDir, true);
            }


            List<Attachment> finalList = new ArrayList<Attachment>();
            for (Attachment attachment : attaches) {
                if (attachment.getAttachId() == null) {
                    attachment.setAttachPath(savePath + File.separator + attachment.getAttachName());
                    attachment.setContactId(id);
                    finalList.add(attachment);
                } else {
                    finalList.add(attachment);
                }
            }

            contactService.setAttaches(id, finalList);
        }

    }


    private void savePhones(Long idContact){
        logger.info("saving phones");
        List<Phone> listPhones = builder.makePhone(request, idContact);
        contactService.setPhone(idContact, listPhones);
    }
}
