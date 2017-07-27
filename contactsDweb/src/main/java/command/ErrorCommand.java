package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/error.jspx";
    }
}
