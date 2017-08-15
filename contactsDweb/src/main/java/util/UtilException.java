package util;

class UtilException extends RuntimeException {

    UtilException(String message, Exception e) {

        super(message, e);
    }
}
