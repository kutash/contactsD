package DAO;

import model.Phone;

public class PhoneDaoFactory {
    public static PhoneDao getPhoneDao(){
        return PhoneDaoImpl.INSTANCE;
    }
}
