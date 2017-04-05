package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Galina on 06.04.2017.
 */
public class ErrorCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/error.jspx";
    }
}
