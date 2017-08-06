package DAO;

import model.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDaoImpl extends AbstractDao implements PhoneDao{

    private Logger logger = LogManager.getLogger(PhoneDaoImpl.class);
    static final PhoneDao INSTANCE = new PhoneDaoImpl();

    public void savePhone(Phone phone){
        logger.info("saving phone contact id {}",phone.getContactId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO telephone(countryCode," +
                    "operatorCode,`number`,kind,comment,contact_id ) VALUES (?,?,?,?,?,?)");
            statement.setString(1,phone.getCountryCode());
            statement.setString(2,phone.getOperatorCode());
            statement.setString(3,phone.getPhoneNumber());
            statement.setString(4,phone.getPhoneType());
            statement.setString(5,phone.getComment());
            statement.setLong(6,phone.getContactId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Exception in savePhone method",e);
        }
        finally {
            close(connection, statement, null);
        }
    }

    public void setPhones(String idContact, List<Phone> phones) {
        deletePhones(idContact);
        for(Phone phone : phones) {
            savePhone(phone);
        }
    }

    public void deletePhones(String contacts) {
        logger.info("deleting phones contacts id {}",contacts);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            String sql = "DELETE FROM telephone WHERE contact_id IN (" + contacts + ")";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Exception in deletePhone method",e);
        } finally {
            close(connection, statement, null);
        }
    }

    public List<Phone> getPhones(Long idContact){
        logger.info("getting phones contact id {}",idContact);
        List<Phone> listPhones = new ArrayList<Phone>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT * FROM telephone WHERE contact_id = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
            while (set.next()) {
                Long idPhone = set.getLong("id_phone");
                String countryCode = set.getString("countryCode");
                String operatorCode = set.getString("operatorCode");
                String number = set.getString("number");
                String type = set.getString("kind");
                String comment = set.getString("comment");
                Long idCont = set.getLong("contact_id");

                Phone phone = new Phone();
                phone.setPhoneId(idPhone);
                phone.setContactId(idCont);
                phone.setComment(comment);
                phone.setCountryCode(countryCode);
                phone.setOperatorCode(operatorCode);
                phone.setPhoneNumber(number);
                phone.setPhoneType(type);
                listPhones.add(phone);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getPhones method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return listPhones;
    }


}
