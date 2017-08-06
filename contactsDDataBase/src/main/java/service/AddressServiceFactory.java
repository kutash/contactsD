package service;

public class AddressServiceFactory {

    public static AddressService getAddressService(){
        return new AddressService();
    }
}
