package DAO;

public class AddressDaoFactory {
    public static AddressDao getAddressDao(){
        return AddressDaoImpl.INSTANCE;
    }
}
