package command;

import model.Attachment;
import model.Contact;
import model.Phone;
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
import java.util.*;

/**
 * Created by Galina on 14.03.2017.
 */
public class AttachCommand implements Command {

    private Logger logger = LogManager.getLogger(AttachCommand.class);
    private Properties properties = new Properties();
    private ContactService contactService = ServiceFactory.getContactService();
    private HttpServletRequest request = null;
    Long idContact = null;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("setting attachments to the session");
        this.request = request;

        try {
            properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        Contact contact = contactService.makeContact(request);
        request.setAttribute("contacts", contact);

        List<Attachment> attachList = (List<Attachment>) session.getAttribute("attaches");

        Map<String,Attachment> attachMap = new HashMap<String, Attachment>();
        if (attachList == null) {
            attachList = new ArrayList<Attachment>();
            Long idContact = contact.getId();
            if(idContact != null) {
                for (Attachment attach : contactService.getAttaches(idContact)) {
                    attachMap.put(attach.getAttachName(),attach);
                }
            }
        }

        for (Attachment attach: attachList) {
            attachMap.put(attach.getAttachName(),attach);
        }

        String attachButton =  request.getParameter("attachButton");
        if (attachButton.equals("add")){
            Attachment attachment = getAttachment(attachMap);
            attachMap.put(attachment.getAttachName(),attachment);
        } else if (attachButton.equals("delete")){
            String [] chosen =  request.getParameterValues("attach_checkbox");
            if(chosen != null) {
                for (String item : chosen) {
                    attachMap.remove(item);
                }
            }
        } else if (attachButton.equals("edit")){
            Attachment attachment = editAttachment();
            attachMap.put(attachment.getAttachName(), attachment);
        }
        try {
            savePhoto();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        List<Phone> phones = contactService.makePhone(request, null);
        request.setAttribute("phones", phones);

        List<Attachment> finalList = new ArrayList<Attachment>();
        finalList.addAll(attachMap.values());

        session.setAttribute("attaches",finalList);

        return "/save.jspx";
    }





    private Attachment getAttachment(Map<String,Attachment> attachMap){
        logger.info("making attachment");
        String savePath = properties.getProperty("TEMP_DIR");
        File attachSaveDir = new File(savePath);

        if (!attachSaveDir.exists()) {
            attachSaveDir.mkdirs();
        }
            String attachName = null;
            java.sql.Date date = new java.sql.Date(new Date().getTime());
            String comment = request.getParameter("comment");
        try {
            Part attachPart = request.getPart("attach");

            String contentDisp = attachPart.getHeader("Content-Disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    attachName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }


            for (Map.Entry<String, Attachment> entry :  attachMap.entrySet()) {

                if (entry.getKey().equals(attachName)){
                    String[] name = attachName.split("\\.");
                    int a = (int) (Math.random() * 25);
                    attachName = name[0]+a+"."+name[1];

                }
            }


            if (attachPart.getSize() > 0) {

                savePath += File.separator + attachName;
                attachPart.write(savePath);
            }
        } catch (IOException e){
            e.printStackTrace();

        } catch (ServletException se){
            se.printStackTrace();
        }

        Attachment attachment = new Attachment(idContact, attachName, comment, date);
        return attachment;
    }


    private Attachment editAttachment(){
        logger.info("edit attachment");
        String attachName = request.getParameter("file_name");
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        String comment = request.getParameter("comment");

        Attachment attachment = new Attachment(idContact, attachName, comment, date);
        return attachment;
    }


    private void savePhoto() throws IOException, ServletException {
        logger.info("saving photo in the temporary directory");
        String photoPath = properties.getProperty("TEMP_PHOTO_DIR");
        File fileSaveDir = new File(photoPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        Part photoPart = request.getPart("photo");
        if (photoPart.getSize() !=0){
            String fileName = "";
            String contentDisp = photoPart.getHeader("Content-Disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }

            photoPath += File.separator + fileName;
            photoPart.write(photoPath);

            HttpSession session = request.getSession();
            session.setAttribute("temp_photo_path", photoPath);
        }
    }
}
