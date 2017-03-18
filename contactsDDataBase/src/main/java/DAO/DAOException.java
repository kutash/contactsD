package DAO;


/**
 * Created by Galina on 11.03.2017.
 */
public class DAOException extends RuntimeException {

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}

