package DAO;


import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.custom.mysql.MysLimitClause;
import com.healthmarketscience.sqlbuilder.dbspec.Function;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import model.Address;
import model.Attachment;
import model.Contact;
import model.Phone;

import java.sql.*;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ContactDAOImpl implements DAO{

    private Logger logger = LogManager.getLogger(ContactDAOImpl.class);
    public static final DAO INSTANCE = new ContactDAOImpl();
    private DataSource source = DaoUtil.getDataSource();

    private ContactDAOImpl() {}


    public List<Contact> getCont(int page) {
        logger.info("getting contacts from page {}", page);
        List<Contact> contacts = new ArrayList<Contact>();
        Connection connection=null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            String sql = "SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `address`.country, `address`.city,`address`.street, `address`.house, `address`.flat, `address`.index, `contact`.company FROM `contact` \n" +
                    "JOIN `address` ON contact.idAddress=address.idAddress limit 10 offset ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 10 * (page - 1));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long contactId = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                Date birthday = resultSet.getDate("birthday");
                String company = resultSet.getString("company");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String house = resultSet.getString("house");
                String flat = resultSet.getString("flat");
                String index = resultSet.getString("index");
                Address address = new Address(country, city, street, house, flat, index);

                Contact tempContact = new Contact(contactId, firstName, middleName, lastName, birthday, address, company);
                contacts.add(tempContact);
            }
        } catch (SQLException e) {
                throw new DAOException("getCont method", e);
        } finally {
            close(connection, statement, resultSet);

        }
        return contacts;
    }




    public int getContactsCount() {
        logger.info("Count the number of contacts");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            connection = source.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) count FROM contact";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = resultSet.getInt("count");

        } catch (SQLException e) {
            throw new DAOException("getContactsCount method",e);
        }  finally {
            close(connection, statement, resultSet);
        }
        return result;
    }


    public Long setAddress(Contact contact){
        logger.info("saving contact address");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        Address address = contact.getAddress();
        Long idAddress = address.getAddressId();

        if(idAddress == null) {

            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("INSERT INTO address " +
                                "(country, city, street, house, flat, `index`) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, address.getCountry());
                statement.setString(2, address.getCity());
                statement.setString(3, address.getStreet());
                statement.setString(4, address.getHouse());
                statement.setString(5, address.getFlat());
                statement.setString(6, address.getIndex());

                statement.executeUpdate();
                generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();

                return generatedKeys.getLong(1);

            } catch (SQLException e) {
                throw new DAOException("setAddress method",e);
            } finally {
                close(connection, statement, generatedKeys);

            }
        } else {
            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("UPDATE address " +
                         "SET country = ? , city = ?, street = ?, house = ?, flat = ?, `index` = ?" +
                         " WHERE idAddress = ?");
                statement.setString(1, address.getCountry());
                statement.setString(2, address.getCity());
                statement.setString(3, address.getStreet());
                statement.setString(4, address.getHouse());
                statement.setString(5, address.getFlat());
                statement.setString(6, address.getIndex());
                statement.setLong(7, idAddress);

                statement.executeUpdate();

                return idAddress;
            } catch (SQLException e) {
                throw new DAOException("setAddress method",e);
            } finally {
                close(connection, statement, null);

            }
        }

    }


    public Long setContact(Contact contact) {

        Long idContact = contact.getId();
        if( idContact == null) {
            return saveContact(contact);
        } else {
            editContact(contact);
            return idContact;
        }
    }



    public Long saveContact(Contact contact) {
        logger.info("saving contact");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO contact(firstName, middleName, " +
                            "lastName, birthday, email," +
                            "sex, status, citizenship, site, company, idAddress) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getMiddleName());
            statement.setString(3, contact.getLastName());
            statement.setDate(4, (java.sql.Date) contact.getBirthday());
            statement.setString(5, contact.getEmail());
            statement.setString(6, contact.getSex());
            statement.setString(7, contact.getStatus());
            statement.setString(8, contact.getCitizen());
            statement.setString(9, contact.getSite());
            statement.setString(10, contact.getCompany());

            Long id = setAddress(contact);
            statement.setLong(11, id);

            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();

            generatedKeys.next();
            return generatedKeys.getLong(1);

        } catch (SQLException e) {
            throw new DAOException("saveContact method", e);
        } finally {
            close(connection, statement, generatedKeys);
        }
    }




    public Contact getById(Long id){
        logger.info("get contact by id");
        Connection connection=null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Contact tempContact=null;
        try {

            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `contact`.email, `contact`.sex, `contact`.`status`, `contact`.citizenship, `contact`.photo, `contact`.site, `address`.country, `address`.city,`address`.street, `address`.house, `address`.flat, `address`.`index`, `contact`.company, `contact`.idAddress FROM `contact` \n" +
                    "JOIN `address` ON contact.idAddress=address.idAddress WHERE id = ?");

            statement.setLong(1,id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long contactId = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                Date birthday = resultSet.getDate("birthday");
                String email = resultSet.getString("email");
                String sex = resultSet.getString("sex");
                String status = resultSet.getString("status");
                String citizenship = resultSet.getString("citizenship");
                String photo = resultSet.getString("photo");
                String site = resultSet.getString("site");
                String company = resultSet.getString("company");
                Long idAddress = resultSet.getLong("idAddress");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String house = resultSet.getString("house");
                String flat = resultSet.getString("flat");
                String index = resultSet.getString("index");

                Address address = new Address(idAddress, country, city, street, house, flat, index);
                tempContact = new Contact(contactId, firstName, middleName, lastName, birthday, citizenship, sex, status, photo, site, email, company, address);

            }
        } catch (SQLException e) {
            throw new DAOException("getById method", e);
        } finally {
            close(connection, statement, resultSet);

        }
        return tempContact;

    }


    public void editContact(Contact contact){
        logger.info("edit contact whit id {}",contact.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
             connection = source.getConnection();
             statement = connection.prepareStatement("UPDATE contact  SET firstName = ?, " +
                    "middleName = ?, lastName = ?," +
                    " birthday = ?,  email = ?, sex = ? , status = ?, citizenship = ?, " +
                    "site = ?, company = ?, idAddress = ? WHERE id = ?");
                statement.setString(1,contact.getFirstName());
                statement.setString(2,contact.getMiddleName());
                statement.setString(3,contact.getLastName());
                statement.setDate(4, (java.sql.Date) contact.getBirthday());
                statement.setString(5,contact.getEmail());
                statement.setString(6,contact.getSex());
                statement.setString(7,contact.getStatus());
                statement.setString(8,contact.getCitizen());
                statement.setString(9,contact.getSite());
                statement.setString(10,contact.getCompany());

                long addressId = setAddress(contact);

                statement.setLong(11, addressId);

                statement.setLong(12, contact.getId());

                statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("editContact method", e);
        }
        finally {
        close(connection, statement, null);
        }
    }



    public String getPhoto(long idContact) {
        logger.info("getting photo from contact with id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
                String path = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT photo FROM contact" +
                     " WHERE id = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
                if (set.next()) {
                    path = set.getString("photo");
                }

        } catch (SQLException e) {
            throw new DAOException("getPhoto method",e);
        }
        finally {
            close(connection, statement, null);
        }
        return path;
    }


    public void setPhoto(long idContact, String path) {
        logger.info("saving photo contact id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("UPDATE contact SET photo = ? " +
                    "WHERE id = ?");
            statement.setString(1, path);
            statement.setLong(2,idContact);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("setPhoto method", e);
        }
        finally {
            close(connection, statement, null);
        }

    }

    public void deleteContact(Long idContact) {
        logger.info("deleting contact with id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = source.getConnection();
            String sql = "DELETE FROM contact WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,idContact);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("deleteContact method", e);
        } finally {
            close(connection, statement, null);
        }

    }


    public void deleteAddress(Long idAddress){
        logger.info("deleting address with id {}",idAddress);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = source.getConnection();
            String sql = "DELETE FROM address WHERE idAddress = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,idAddress);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("deleteAddress method", e);
        } finally {
            close(connection, statement, null);
        }
    }


    public void setAttaches(Long idContact, List<Attachment> attachList){
        deleteAttachment(idContact);
        for (Attachment attachment : attachList){
            saveAttaches(attachment);
        }
    }


    public void saveAttaches(Attachment attachment){
        logger.info("saving attachment");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO attachment(path,`name`," +
                    "`date`,comment,idContact ) VALUES (?,?,?,?,?)");
                statement.setString(1, attachment.getAttachPath());
                statement.setString(2, attachment.getAttachName());
                java.sql.Date d = (java.sql.Date) attachment.getDate();
                statement.setDate(3, (java.sql.Date) attachment.getDate());
                statement.setString(4, attachment.getComment());
                statement.setLong(5,attachment.getContactId());
                statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("saveAttaches method",e);
        }
        finally {
            close(connection, statement, null);
        }
    }


    public List<Attachment> getAttaches(Long idContact){
        logger.info("getting List of attachments from contact id {}",idContact);
        List<Attachment> listAttaches = new ArrayList<Attachment>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
             connection = source.getConnection();
             statement = connection.prepareStatement("SELECT * FROM attachment WHERE idContact = ?");
             statement.setLong(1, idContact);
             set = statement.executeQuery();
                while (set.next()) {

                    Long idCont = set.getLong("idContact");
                    String comment = set.getString("comment");
                    String attachName = set.getString("name");
                    Date attachDate = set.getDate("date");
                    String attachPath = set.getString("path");
                    Long idAttach = set.getLong("idAttach");
                    Attachment attach = new Attachment(idCont,attachName,attachPath,comment,attachDate,idAttach);
                    listAttaches.add(attach);

                }

        } catch (SQLException e) {
            throw new DAOException("getAttaches method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return listAttaches;
    }




    public Attachment getAttach(Long idContact){
        logger.info("getting attachment from contact id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Attachment attach = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT * FROM attachment WHERE idContact = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
            while (set.next()) {

                Long idCont = set.getLong("idContact");
                String comment = set.getString("comment");
                String attachName = set.getString("name");
                Date attachDate = set.getDate("date");
                String attachPath = set.getString("path");
                Long idAttach = set.getLong("idAttach");
                attach = new Attachment(idCont,attachName,attachPath,comment,attachDate,idAttach);


            }

        } catch (SQLException e) {
            throw new DAOException("getAttach method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return attach;
    }


    public void deleteAttachment(Long idContact) {
        logger.info("deleting attachment contact id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = source.getConnection();
            String sql = "DELETE FROM attachment WHERE idContact = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,idContact);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("deleteAttachment method",e);
        } finally {
            close(connection, statement, null);
        }

    }



    public void savePhone(Phone phone){
        logger.info("saving phone contact id {}",phone.getContactId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO telephone(countryCode," +
                    "operatorCode,`number`,kind,comment,idContact ) VALUES (?,?,?,?,?,?)");
                statement.setString(1,phone.getCountryCode());
                statement.setString(2,phone.getOperatorCode());
                statement.setString(3,phone.getPhoneNumber());
                statement.setString(4,phone.getPhoneType());
                statement.setString(5,phone.getComment());
                statement.setLong(6,phone.getContactId());

                statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("savePhone method",e);
        }
        finally {
            close(connection, statement, null);
        }
    }


    public void setPhones(long idContact, List<Phone> phones) {
        deletePhones(idContact);
        for(Phone phone : phones) {
            savePhone(phone);
        }
    }



    public void deletePhones(Long idContact) {
        logger.info("deleting phones contact id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = source.getConnection();
            String sql = "DELETE FROM telephone WHERE idContact = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,idContact);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("deletePhone method",e);
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
            statement = connection.prepareStatement("SELECT * FROM telephone WHERE idContact = ?");
            statement.setLong(1, idContact);
            set = statement.executeQuery();
            while (set.next()) {
                Long idPhone = set.getLong("idPhone");
                String countryCode = set.getString("countryCode");
                String operatorCode = set.getString("operatorCode");
                String number = set.getString("number");
                String type = set.getString("kind");
                String comment = set.getString("comment");
                Long idCont = set.getLong("idContact");
                Phone phone = new Phone(idPhone, countryCode, operatorCode, number, type, comment, idCont);
                listPhones.add(phone);

            }

        } catch (SQLException e) {
            throw new DAOException("getPhones method",e);
        }
        finally {
            close(connection,statement,set);
        }
        return listPhones;
    }



    public List<Contact> searchContacts(Map<String, String> params, int page) {
        logger.info("searching contacts");

        String query = "SELECT * from contact AS c INNER JOIN address AS a ON c.idAddress = a.idAddress";

        String s=" where ";
        if (params.get("firstName") !=null && !equals("")){
            query+=s+"firstName like '"+params.get("firstName")+"'";
            s = " and ";
        }
        if (params.get("middleName") !=null && !equals("")){
            query+=s+"middleName like '"+params.get("middleName")+"'";
            s = " and ";
        }
        if (params.get("lastName") !=null && !equals("")){
            query+=s+"lastName like '"+params.get("lastName")+"'";
            s = " and ";
        }
        if (params.get("sex") !=null && !equals("")){
            query+=s+"sex like '"+params.get("sex")+"'";
            s = " and ";
        }
        if (params.get("status") !=null && !equals("")){
            query+=s+"status like '"+params.get("status")+"'";
        }
        if (params.get("citizenship") !=null && !equals("")){
            query+=s+"citizenship like '"+params.get("citizenship")+"'";
            s = " and ";
        }
        if (params.get("country") !=null && !equals("")){
            query+=s+"country like '"+params.get("country")+"'";
            s = " and ";
        }
        if (params.get("city") !=null && !equals("")){
            query+=s+"city like '"+params.get("city")+"'";
            s = " and ";
        }
        if (params.get("street") !=null && !equals("")){
            query+=s+"street like '"+params.get("street")+"'";
            s = " and ";
        }
        if (params.get("house") !=null && !equals("")){
            query+=s+"house like '"+params.get("house")+"'";
            s = " and ";
        }
        if (params.get("flat") !=null && !equals("")){
            query+=s+"flat like '"+params.get("flat")+"'";
            s = " and ";
        }
        if (params.get("index") !=null && !equals("")){
            query+=s+"`index` like '"+params.get("index")+"'";
            s = " and ";
        }
        if ((params.get("birthSince") !=null && !equals("")) && (params.get("birthUpto") !=null && !equals(""))){
            query += s + "birthday BETWEEN '"+params.get("birthSince")+"' AND '"+params.get("birthUpto")+"'";
        } else if ((params.get("birthSince") !=null && !equals(""))){
            query+=s+"birthday >= '"+params.get("birthSince")+"'";
        }else if ((params.get("birthUpto") !=null && !equals(""))){
            query+=s+"birthday <= '"+params.get("birthUpto")+"'";
        }

        query+=" limit 10 offset ?";

        System.out.println(query);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> searchResult = new ArrayList<Contact>();
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, 10 * (page - 1));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long contactId = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                Date birthday = resultSet.getDate("birthday");
                String status = resultSet.getString("status");
                String sex = resultSet.getString("sex");
                String citizenship = resultSet.getString("citizenship");
                String webSite = resultSet.getString("site");
                String email = resultSet.getString("email");
                String photo = resultSet.getString("photo");
                String company = resultSet.getString("company");

                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String house = resultSet.getString("house");
                String flat = resultSet.getString("flat");
                String index = resultSet.getString("index");
                Address address = new Address(country, city, street, house, flat, index);


                Contact contact = new Contact(contactId, firstName,  middleName, lastName, birthday, citizenship, sex, status,
                        photo, webSite, email, company, address);

                searchResult.add(contact);
            }
        } catch (SQLException e) {
            throw new DAOException("searchContacts method",e);
        } finally {
            close(connection, statement, resultSet);
        }
        return searchResult;
    }




    public int countForSearch(Map<String, String> params){
        logger.info("Count the number of contacts for search");
        String query = "SELECT COUNT(*) AS total from contact AS c INNER JOIN address AS a ON c.idAddress = a.idAddress";

        String s=" where ";
        if (params.get("firstName") !=null && !equals("")){
            query+=s+"firstName like '"+params.get("firstName")+"'";
            s = " and ";
        }
        if (params.get("middleName") !=null && !equals("")){
            query+=s+"middleName like '"+params.get("middleName")+"'";
            s = " and ";
        }
        if (params.get("lastName") !=null && !equals("")){
            query+=s+"lastName like '"+params.get("lastName")+"'";
            s = " and ";
        }
        if (params.get("sex") !=null && !equals("")){
            query+=s+"sex like '"+params.get("sex")+"'";
            s = " and ";
        }
        if (params.get("status") !=null && !equals("")){
            query+=s+"status like '"+params.get("status")+"'";
        }
        if (params.get("citizenship") !=null && !equals("")){
            query+=s+"citizenship like '"+params.get("citizenship")+"'";
            s = " and ";
        }
        if (params.get("country") !=null && !equals("")){
            query+=s+"country like '"+params.get("country")+"'";
            s = " and ";
        }
        if (params.get("city") !=null && !equals("")){
            query+=s+"city like '"+params.get("city")+"'";
            s = " and ";
        }
        if (params.get("street") !=null && !equals("")){
            query+=s+"street like '"+params.get("street")+"'";
            s = " and ";
        }
        if (params.get("house") !=null && !equals("")){
            query+=s+"house like '"+params.get("house")+"'";
            s = " and ";
        }
        if (params.get("flat") !=null && !equals("")){
            query+=s+"flat like '"+params.get("flat")+"'";
            s = " and ";
        }
        if (params.get("index") !=null && !equals("")){
            query+=s+"`index` like '"+params.get("index")+"'";
            s = " and ";
        }
        if ((params.get("birthSince") !=null && !equals("")) && (params.get("birthUpto") !=null && !equals(""))){
            query += s + "birthday BETWEEN '"+params.get("birthSince")+"' AND '"+params.get("birthUpto")+"'";
        } else if ((params.get("birthSince") !=null && !equals(""))){
            query+=s+"birthday >= '"+params.get("birthSince")+"'";
        }else if ((params.get("birthUpto") !=null && !equals(""))){
            query+=s+"birthday <= '"+params.get("birthUpto")+"'";
        }


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            connection = source.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            throw new DAOException("getContactsCount method",e);
        }  finally {
            close(connection, statement, resultSet);
        }
        return result;
    }



    public List<Contact> getContactsForBirthday() {
        logger.info("searching contacts for birthday");
        List<Contact> contacts = new ArrayList<Contact>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = source.getConnection();
            String sql = "SELECT id, firstName, lastName, middleName, email FROM contact WHERE " +
                    "MONTH(birthday) = MONTH(NOW()) AND DAY(birthday) = DAY(NOW());";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                String email = resultSet.getString("email");
                Contact contact = new Contact(id, firstName, middleName, lastName, email);

                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new DAOException("getContactsForBirthday method", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return contacts;
    }




    public static void close(Connection connection, Statement statement, ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            throw new DAOException("close method", se);
        }
    }
}
