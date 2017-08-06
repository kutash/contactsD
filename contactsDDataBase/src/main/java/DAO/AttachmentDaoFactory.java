package DAO;

public class AttachmentDaoFactory {

    public static AttachmentDao getAttachmentDao(){
        return AttachmentDaoImpl.INSTANCE;
    }
}
