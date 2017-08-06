package DAO;

import model.Address;
import model.Contact;
import java.sql.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactDaoImpl extends AbstractDao implements ContactDao {

    private Logger logger = LogManager.getLogger(ContactDaoImpl.class);
    static final ContactDao INSTANCE = new ContactDaoImpl();

    private ContactDaoImpl() {}

    public List<Contact> getContacts(int page) {
        logger.info("getting contacts from page {}", page);
        List<Contact> contacts = new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            String sql = "SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `address`.country, `address`.city,`address`.street, `address`.house, `address`.flat, `address`.index, `contact`.company FROM `contact` \n" +
                    "JOIN `address` ON contact.id=address.contact_id limit 10 offset ?";
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

                Address address = new Address();
                address.setCountry(country);
                address.setCity(city);
                address.setStreet(street);
                address.setHouse(house);
                address.setFlat(flat);
                address.setIndex(index);

                Contact tempContact = new Contact();
                tempContact.setId(contactId);
                tempContact.setFirstName(firstName);
                tempContact.setLastName(lastName);
                tempContact.setMiddleName(middleName);
                tempContact.setBirthday(birthday);
                tempContact.setCompany(company);
                tempContact.setAddress(address);

                contacts.add(tempContact);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getContacts method", e);
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
        int result;
        try {
            connection = source.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) count FROM contact";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = resultSet.getInt("count");

        } catch (SQLException e) {
            throw new DAOException("Exception in getContactsCount method",e);
        }  finally {
            close(connection, statement, resultSet);
        }
        return result;
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
                            "sex, status, citizenship, site, company) VALUES (?,?,?,?,?,?,?,?,?,?)",
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

            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            return generatedKeys.getLong(1);

        } catch (SQLException e) {
            throw new DAOException("Exception in saveContact method", e);
        } finally {
            close(connection, statement, generatedKeys);
        }
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
                    "site = ?, company = ? WHERE id = ?");
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
            statement.setLong(11, contact.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Exception in editContact method", e);
        }
        finally {
            close(connection, statement, null);
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
            statement = connection.prepareStatement("SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `contact`.email, `contact`.sex, `contact`.`status`, `contact`.citizenship, `contact`.photo, `contact`.site, `address`.country, `address`.city,`address`.street, `address`.house, `address`.flat, `address`.`index`, `contact`.company, `address`.id_address FROM `contact` \n" +
                    "JOIN `address` ON contact.id=address.contact_id WHERE id = ?");

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
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String house = resultSet.getString("house");
                String flat = resultSet.getString("flat");
                String index = resultSet.getString("index");
                Long idAddress = resultSet.getLong("id_address");

                Address address = new Address();
                address.setCountry(country);
                address.setCity(city);
                address.setStreet(street);
                address.setHouse(house);
                address.setFlat(flat);
                address.setIndex(index);
                address.setAddressId(idAddress);

                tempContact = new Contact();
                tempContact.setId(contactId);
                tempContact.setFirstName(firstName);
                tempContact.setLastName(lastName);
                tempContact.setMiddleName(middleName);
                tempContact.setBirthday(birthday);
                tempContact.setCompany(company);
                tempContact.setAddress(address);
                tempContact.setEmail(email);
                tempContact.setSex(sex);
                tempContact.setStatus(status);
                tempContact.setCitizen(citizenship);
                tempContact.setPhoto(photo);
                tempContact.setSite(site);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getById method", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return tempContact;
    }

    public String getPhoto(long idContact) {
        logger.info("getting photo from contact with id {}",idContact);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set;
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
            throw new DAOException("Exception in getPhoto method",e);
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
            throw new DAOException("Exception in setPhoto method", e);
        }
        finally {
            close(connection, statement, null);
        }
    }

    /*public void deleteContact(Long idContact) {
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
            throw new DAOException("Exception in deleteContact method", e);
        } finally {
            close(connection, statement, null);
        }
    }*/


    public void deleteContact(String contacts) {
        logger.info("deleting contacts");
        Connection connection = null;
        Statement statement = null;
        try {
            connection = source.getConnection();
            String sql = "DELETE FROM contact WHERE id IN (" + contacts + ")";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {

        } finally {
            close(connection, statement, null);
        }
    }

    private String buildQuery(Map<String, String> params){
        StringBuilder query = new StringBuilder();
        String s=" where ";
        if (params.get("firstName") !=null){
            query.append(s);
            query.append("firstName like '%");
            query.append(params.get("firstName"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("middleName") !=null){
            query.append(s);
            query.append("middleName like '%");
            query.append(params.get("middleName"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("lastName") !=null){
            query.append(s);
            query.append("lastName like '%");
            query.append(params.get("lastName"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("sex") !=null){
            query.append(s);
            query.append("sex like '%");
            query.append(params.get("sex"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("status") !=null){
            query.append(s);
            query.append("status like '%");
            query.append(params.get("status"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("citizenship") !=null){
            query.append(s);
            query.append("citizenship like '%");
            query.append(params.get("citizenship"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("country") !=null){
            query.append(s);
            query.append("country like '%");
            query.append(params.get("country"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("city") !=null){
            query.append(s);
            query.append("city like '%");
            query.append(params.get("city"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("street") !=null){
            query.append(s);
            query.append("street like '%");
            query.append(params.get("street"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("house") !=null){
            query.append(s);
            query.append("house like '%");
            query.append(params.get("house"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("flat") !=null){
            query.append(s);
            query.append("flat like '%");
            query.append(params.get("flat"));
            query.append("%'");
            s = " and ";
        }
        if (params.get("index") !=null){
            System.out.println(params.get("index"));
            query.append(s);
            query.append("`index` like '%");
            query.append(params.get("index"));
            query.append("%'");
            s = " and ";
        }
        if ((params.get("birthSince") !=null) && (params.get("birthUpto") !=null)){
            query.append(s);
            query.append("birthday BETWEEN '");
            query.append(params.get("birthSince"));
            query.append("' AND '");
            query.append(params.get("birthUpto"));
            query.append("'");
        } else if ((params.get("birthSince") !=null)){
            query.append(s);
            query.append("birthday >= '");
            query.append(params.get("birthSince"));
            query.append("'");
        }else if ((params.get("birthUpto") !=null)){
            query.append(s);
            query.append("birthday <= '");
            query.append(params.get("birthUpto"));
            query.append("'");
        }
        return query.toString();
    }

    public List<Contact> searchContacts(Map<String, String> params, int page) {
        logger.info("searching contacts");
        String query = "SELECT * from contact AS c INNER JOIN address AS a ON c.id = a.contact_id";
        String resultQuery = query + buildQuery(params) + " limit 10 offset ?";
        System.out.println(resultQuery+"=========================");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> searchResult = new ArrayList<Contact>();
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement(resultQuery);
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

                Address address = new Address();
                address.setCountry(country);
                address.setCity(city);
                address.setStreet(street);
                address.setHouse(house);
                address.setFlat(flat);
                address.setIndex(index);

                Contact tempContact = new Contact();
                tempContact.setId(contactId);
                tempContact.setFirstName(firstName);
                tempContact.setLastName(lastName);
                tempContact.setMiddleName(middleName);
                tempContact.setBirthday(birthday);
                tempContact.setCompany(company);
                tempContact.setAddress(address);
                tempContact.setEmail(email);
                tempContact.setSex(sex);
                tempContact.setStatus(status);
                tempContact.setCitizen(citizenship);
                tempContact.setPhoto(photo);
                tempContact.setSite(webSite);

                searchResult.add(tempContact);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in searchContacts method",e);
        } finally {
            close(connection, statement, resultSet);
        }
        return searchResult;
    }

    public int countForSearch(Map<String, String> params){

        logger.info("Count the number of contacts for search");
        String query = "SELECT COUNT(*) AS total from contact AS c INNER JOIN address AS a ON c.id = a.contact_id";
        String resultQuery = query + buildQuery(params);
        System.out.println(resultQuery+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            connection = source.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(resultQuery);
            while (resultSet.next()) {
                result = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            throw new DAOException("Exception in getContactsCount method",e);
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

                Contact tempContact = new Contact();
                tempContact.setFirstName(firstName);
                tempContact.setLastName(lastName);
                tempContact.setMiddleName(middleName);
                tempContact.setEmail(email);

                contacts.add(tempContact);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in getContactsForBirthday method", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return contacts;
    }

}
