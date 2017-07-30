package builder;

public class ObjectBuilderException extends RuntimeException {

    public ObjectBuilderException(Exception e) {

        super(e);
    }

    ObjectBuilderException(String message, Exception e) {

        super(message, e);
    }
}
