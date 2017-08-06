package DAO;

import model.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDaoImpl extends AbstractDao implements AttachmentDao {

    private Logger logger = LogManager.getLogger(AttachmentDaoImpl.class);
    static final AttachmentDao INSTANCE = new AttachmentDaoImpl();

    public void setAttaches(String idContact, List<Attachment> attachList){
        deleteAttachment(idContact);
        for (Attachment attachment : attachList){
            saveAttaches(attachment);
        }
    }

    public void saveAttaches(Attachment attachment){
        logger.info("saving attachments");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO attachment(path,`name`," +
                    "`date`,comment, contact_id ) VALUES (?,?,?,?,?)");
            statement.setString(1, attachment.getAttachPath());
            statement.setString(2, attachment.getAttachName());
            java.sql.Date d = (java.sql.Date) attachment.getDate();
            statement.setDate(3, (java.sql.Date) attachment.getDate());
            statement.setString(4, attachment.getComment());
            statement.setLong(5,attachment.getContactId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Exception in saveAttaches method",e);
        }
        finally {
            close(connection, statement, null);
        }
    }

    public List<Attachment> getAttaches(Long idContact){
        logger.info("getting List of attachments from contact id {}",idContact);
        List<Attachment> listAttaches = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT * FROM attachment WHERE contact_id = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
            while (set.next()) {
                Long idCont = set.getLong("contact_id");
                String comment = set.getString("comment");
                String attachName = set.getString("name");
                Date attachDate = set.getDate("date");
                String attachPath = set.getString("path");
                Long idAttach = set.getLong("id_attach");

                Attachment attachment = new Attachment();
                attachment.setAttachName(attachName);
                attachment.setComment(comment);
                attachment.setDate(attachDate);
                attachment.setContactId(idCont);
                attachment.setAttachId(idAttach);
                attachment.setContactId(idContact);
                attachment.setAttachPath(attachPath);
                listAttaches.add(attachment);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getAttaches method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return listAttaches;
    }

    /*public Attachment getAttach(Long idContact){
        logger.info("getting attachment from contact id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Attachment attach = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT * FROM attachment WHERE contact_id = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
            while (set.next()) {
                Long idCont = set.getLong("contact_id");
                String comment = set.getString("comment");
                String attachName = set.getString("name");
                Date attachDate = set.getDate("date");
                String attachPath = set.getString("path");
                Long idAttach = set.getLong("id_attach");
                attach = new Attachment(idCont,attachName,attachPath,comment,attachDate,idAttach);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getAttach method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return attach;
    }*/

    public void deleteAttachment(String contacts) {
        logger.info("deleting attachments contacts id {}",contacts);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            String sql = "DELETE FROM attachment WHERE contact_id IN (" + contacts + ")";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Exception in deleteAttachment method",e);
        } finally {
            close(connection, statement, null);
        }
    }
}
