package command;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class SearchCommand implements Command {

    private Logger logger = LogManager.getLogger(SearchCommand.class);
    private List<String> parametersList = Arrays.asList("firstName", "lastName", "middleName", "sex", "status",
            "citizenship", "country", "city", "street", "house", "flat", "index", "birthSince", "birthUpto");


    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("searching contacts");
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (String param : parametersList) {
            String value = request.getParameter(param).trim();
            if (StringUtils.isNotEmpty(value)) {
                params.put(param, value);
            }
        }
        request.getSession().setAttribute("params",params);
        request.getSession().setAttribute("isSearch",true);
        return "/my-servlet?command=show";

    }
}
