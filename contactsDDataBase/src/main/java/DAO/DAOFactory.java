package DAO;

public class DAOFactory {
    public static DAO getContactDao(){
        return ContactDAOImpl.INSTANCE;
    }
}
