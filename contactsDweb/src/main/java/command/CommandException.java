package command;

public class CommandException extends RuntimeException {

    public CommandException(Exception e) {

        super(e);
    }

    CommandException(String message, Exception e) {

        super(message, e);
    }
}
