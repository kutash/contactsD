package DAO;

/**
 * Created by Galina on 11.03.2017.
 */
public class DAOFactory {
    public static DAO getContactDao(){
        return ContactDAOImpl.INSTANCE;
    }
}
