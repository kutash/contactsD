package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Galina on 08.04.2017.
 */
public class CancelCommand implements Command {

    private Logger logger = LogManager.getLogger(CancelCommand.class);
    HttpSession session;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("cancel saving");
        session = request.getSession();
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");
        return "/save.jspx";
    }
}
