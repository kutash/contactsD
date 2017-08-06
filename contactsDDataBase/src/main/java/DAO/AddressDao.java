package DAO;

import model.Contact;

public interface AddressDao {

    void deleteAddress(String idAddress);

    Long setAddress(Contact contact);
}
