package service;

public class PhoneServiceFactory {

    public static PhoneService getPhoneService(){

        return new PhoneService();
    }
}
