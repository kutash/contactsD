package command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = new ErrorCommand();
        String action = request.getParameter("command");
        if (action == null) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new CommandException("Exception in making new command", e);
        }
        return current;
    }
}
