package command;

class CommandException extends RuntimeException {

    CommandException(String message, Exception e) {

        super(message, e);
    }
}
