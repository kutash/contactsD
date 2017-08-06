package DAO;

import model.Address;
import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class AddressDaoImpl extends AbstractDao implements AddressDao {

    private Logger logger = LogManager.getLogger(AddressDaoImpl.class);
    static final AddressDao INSTANCE = new AddressDaoImpl();

    public Long setAddress(Contact contact) {

        logger.info("saving contact address");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        Address address = contact.getAddress();
        Long idAddress = address.getAddressId();
        System.out.println(idAddress);
        if(idAddress == null) {
            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("INSERT INTO address " +
                                "(contact_id, country, city, street, house, flat, `index`) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, contact.getId());
                statement.setString(2, address.getCountry());
                statement.setString(3, address.getCity());
                statement.setString(4, address.getStreet());
                statement.setString(5, address.getHouse());
                statement.setString(6, address.getFlat());
                statement.setString(7, address.getIndex());

                statement.executeUpdate();
                generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();

                return generatedKeys.getLong(1);

            } catch (SQLException e) {
                throw new DAOException("Exception in setAddress method",e);
            } finally {
                close(connection, statement, generatedKeys);
            }
        } else {
            try {
                connection = source.getConnection();
                statement = connection.prepareStatement("UPDATE address " +
                        "SET country = ? , city = ?, street = ?, house = ?, flat = ?, `index` = ?" +
                        " WHERE id_address = ?");
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
                throw new DAOException("Exception in setAddress method",e);
            } finally {
                close(connection, statement, null);
            }
        }
    }

    public void deleteAddress(String contacts){
        logger.info("deleting address");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = source.getConnection();
            String sql = "DELETE FROM address WHERE contact_id IN (" + contacts + ")";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Exception in deleteAddress method", e);
        } finally {
            close(connection, statement, null);
        }
    }
}
