package DAO;


import model.Address;
import model.Attachment;
import model.Contact;
import model.Phone;

import java.sql.*;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ContactDAOImpl implements DAO{

    public static final DAO INSTANCE = new ContactDAOImpl();
    private DataSource source = DaoUtil.getDataSource();

    private ContactDAOImpl() {}


    public List<Contact> getCont(int page) {
        List<Contact> contacts = new ArrayList<Contact>();
        Connection connection=null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            String sql = "SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `address`.country, `address`.city,`address`.address, `address`.index, `contact`.company FROM `contact` \n" +
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
                String theAddress = resultSet.getString("address");

                String index = resultSet.getString("index");
                Address address = new Address(country, city, theAddress, index);

                Contact tempContact = new Contact(contactId, firstName, middleName, lastName, birthday, address, company);
                contacts.add(tempContact);
            }
        } catch (SQLException e) {
                throw new DAOException(e);
        } finally {
            close(connection, statement, resultSet);

        }

        return contacts;
    }

    public int getContactsCount() {
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
            throw new DAOException(e);
        }  finally {
            close(connection, statement, resultSet);
        }
        return result;
    }


    public Long setAddress(Contact contact){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        Address address = contact.getAddress();
        Long idAddress = address.getAddressId();

        if(idAddress == null) {

            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("INSERT INTO address " +
                                "(country, city, address, `index`) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, address.getCountry());
                statement.setString(2, address.getCity());
                statement.setString(3, address.getAddress());
                statement.setString(4, address.getIndex());

                statement.executeUpdate();
                generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();

                return generatedKeys.getLong(1);

            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                close(connection, statement, null);

            }
        } else {
            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("UPDATE address " +
                         "SET country = ? , city = ?, address = ?, `index` = ?" +
                         " WHERE idAddress = ?");
                statement.setString(1, address.getCountry());
                statement.setString(2, address.getCity());
                statement.setString(3, address.getAddress());
                statement.setString(4, address.getIndex());
                statement.setLong(5, idAddress);

                statement.executeUpdate();

                return idAddress;
            } catch (SQLException e) {
                throw new DAOException(e);
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
            throw new DAOException(e);
        } finally {
            close(connection, statement, generatedKeys);
        }
    }







    public Contact getById(Long id){

        Connection connection=null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Contact tempContact=null;
        try {

            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT `contact`.id, `contact`.firstName, `contact`.middleName, `contact`.lastName, `contact`.birthday, `contact`.email, `contact`.sex, `contact`.`status`, `contact`.citizenship, `contact`.photo, `contact`.site, `address`.country, `address`.city,`address`.address, `address`.`index`, `contact`.company FROM `contact` \n" +
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

                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String theAddress = resultSet.getString("address");
                String index = resultSet.getString("index");

                Address address = new Address(country, city, theAddress, index);
                tempContact = new Contact(contactId, firstName, middleName, lastName, birthday, citizenship, sex, status, photo, site, email, company, address);

            }
        } catch (SQLException e) {

        } finally {
            close(connection, statement, resultSet);

        }
        return tempContact;

    }


    public void editContact(Contact contact){

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
            throw new DAOException(e);
        }
        finally {
        close(connection, statement, null);
        }
    }



    public String getPhoto(long idContact) {
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
            throw new DAOException(e);
        }
        finally {
            close(connection, statement, null);
        }
        return path;
    }

    public void setPhoto(long idContact, String path) {
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
            throw new DAOException(e);
        }
        finally {
            close(connection, statement, null);
        }

    }

    public void deleteContact(Long idContact) {

    }


    public void saveAttaches(Attachment attachment){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("INSERT INTO attachment(path,`name`," +
                    "`date`,comment,idContact ) VALUES (?,?,?,?,?)");
                statement.setString(1, attachment.getAttachPath());
                statement.setString(2, attachment.getAttachName());
                statement.setDate(3, (java.sql.Date) attachment.getDate());
                statement.setString(4, attachment.getComment());
                statement.setLong(5,attachment.getContactId());
                statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            close(connection, statement, null);
        }
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

        }
    }
}
