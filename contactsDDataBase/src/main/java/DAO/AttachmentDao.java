package DAO;

import model.Attachment;
import java.util.List;

public interface AttachmentDao {

    void saveAttaches(Attachment attachment);

    List<Attachment> getAttaches(Long idContact);

    void setAttaches(String idContact, List<Attachment> attachList);

    void deleteAttachment(String idContact);
}
