package service;

import DAO.AddressDao;
import DAO.AddressDaoFactory;
import model.Contact;

public class AddressService {

    private AddressDao addressDAO = AddressDaoFactory.getAddressDao();

    public void deleteAddress(String idAddress){
        addressDAO.deleteAddress(idAddress);
    }

    public void saveAddress(Contact contact) {
        addressDAO.setAddress(contact);
    }
}
