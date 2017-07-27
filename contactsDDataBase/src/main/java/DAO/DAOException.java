package DAO;

public class DAOException extends RuntimeException {

    public DAOException(Exception e) {
        super(e);
    }

    DAOException(String message, Exception e) {
        super(message, e);
    }
}

