package command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Galina on 13.03.2017.
 */
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
            e.printStackTrace();
        }
        return current;
    }
}
