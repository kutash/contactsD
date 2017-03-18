package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Galina on 13.03.2017.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
