package command;

import service.AttachmentService;
import service.AttachmentServiceFactory;
import util.Builder;
import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.FileManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AttachCommand implements Command {

    private Logger logger = LogManager.getLogger(AttachCommand.class);
    private Properties properties = new Properties();
    private AttachmentService attachmentService = AttachmentServiceFactory.getAttachmentService();
    private Builder builder = new Builder();
    private HttpServletRequest request = null;
    private Long idContact = null;

    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("setting attachments to the session");
        this.request = request;
        try {
            properties.load(AttachCommand.class.getResourceAsStream("/attachment.properties"));
        } catch (IOException e) {
            throw new CommandException("Exception while loading properties", e);
        }
        HttpSession session = request.getSession();
        Contact contact = builder.makeContact(request);
        request.setAttribute("contacts", contact);
        List<Attachment> attachments;
        try {
            attachments = (List<Attachment>) session.getAttribute("attaches");
        }catch (ClassCastException e){
            throw new CommandException("Exception in casting types", e);
        }
        Map<String,Attachment> attachMap = new HashMap<>();
        if (attachments == null) {
            attachments = new ArrayList<>();
            idContact = contact.getId();
            if(idContact != null) {
                for (Attachment attach : attachmentService.getAttaches(idContact)) {
                    attachMap.put(attach.getAttachName(),attach);
                }
            }
        }
        for (Attachment attach: attachments) {
            attachMap.put(attach.getAttachName(),attach);
        }
        String attachButton =  request.getParameter("attachButton");
        Attachment attachment;
        switch (attachButton) {
            case "add":
            attachment = getAttachment(attachMap);
            attachMap.put(attachment.getAttachName(),attachment);
            break;
            case "delete":
            deleteAttach(attachMap);
            break;
            case "edit":
            attachment = editAttachment();
            attachMap.put(attachment.getAttachName(), attachment);
            break;
        }
        savePhoto();
        List<Phone> phones = builder.makePhone(request, null);
        request.setAttribute("phones", phones);
        List<Attachment> finalList = new ArrayList<>();
        finalList.addAll(attachMap.values());
        session.setAttribute("attaches",finalList);
        return "/save.jspx";
    }

    private void deleteAttach(Map<String,Attachment> attachMap){

        String [] chosen =  request.getParameterValues("attach_checkbox");
        if(chosen != null) {
            for (String item : chosen) {
               Attachment attachment = attachMap.get(item);
               String attachPath = attachment.getAttachPath();
               if(attachPath != null) {
                   new FileManager().deleteFile(attachPath);
               }
                attachMap.remove(item);
            }
        }
    }

    private Attachment getAttachment(Map<String,Attachment> attachMap) {

        logger.info("making attachment");
        String savePath = properties.getProperty("TEMP_DIR");
        File attachSaveDir = new File(savePath);
        if (!attachSaveDir.exists()) {
            boolean created = attachSaveDir.mkdirs();
            if (!created){
                logger.debug("Directory wasn't created");
            }
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
        } catch (Exception e){
            throw  new CommandException("Exception in making attachment",e);
        }
        Attachment attachment = new Attachment();
        attachment.setAttachName(attachName);
        attachment.setComment(comment);
        attachment.setDate(date);
        attachment.setContactId(idContact);
        attachment.setAttachPath(savePath);
        return attachment;
    }

    private Attachment editAttachment(){
        logger.info("edit attachment");
        String attachName = request.getParameter("file_name");
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        String comment = request.getParameter("comment");
        Attachment attachment = new Attachment();
        attachment.setAttachName(attachName);
        attachment.setComment(comment);
        attachment.setDate(date);
        return attachment;
    }

    private void savePhoto() {

        logger.info("saving photo in the temporary directory");
        String photoPath = properties.getProperty("TEMP_PHOTO_DIR");
        File fileSaveDir = new File(photoPath);
        try {
            if (!fileSaveDir.exists()) {
                boolean created = fileSaveDir.mkdir();
                if (!created){
                    logger.debug("Directory wasn't created");
                }
            }
            Part photoPart = request.getPart("photo");
            if (photoPart.getSize() != 0) {
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
        } catch (Exception e){
            throw new CommandException("Exception in savePhoto method",e);
        }
    }
}
