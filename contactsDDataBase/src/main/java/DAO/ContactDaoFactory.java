package DAO;

public class ContactDaoFactory {
    public static ContactDao getContactDao(){
        return ContactDaoImpl.INSTANCE;
    }
}
