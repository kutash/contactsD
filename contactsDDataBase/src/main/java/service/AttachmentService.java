package service;

import DAO.AttachmentDao;
import DAO.AttachmentDaoFactory;
import model.Attachment;
import java.util.List;

public class AttachmentService {

    private AttachmentDao attachmentDAO = AttachmentDaoFactory.getAttachmentDao();

    public List<Attachment> getAttaches(Long idContact){

        return attachmentDAO.getAttaches(idContact);
    }

    public void setAttaches(String idContact, List<Attachment> attachList){

        attachmentDAO.setAttaches(idContact, attachList);
    }

    /*public Attachment getAttach(Long idContact){

        return contactDAO.getAttach(idContact);
    }*/

    public void deleteAttachment(String idContact){

        attachmentDAO.deleteAttachment(idContact);
    }
}
