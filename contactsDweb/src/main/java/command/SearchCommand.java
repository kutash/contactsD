package command;

import model.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Galina on 14.03.2017.
 */
public class SearchCommand implements Command {

    private Logger logger = LogManager.getLogger(SearchCommand.class);
    private List<String> parametersList = Arrays.asList("firstName", "lastName", "middleName", "sex", "status",
            "citizenship", "country", "city", "address", "birthSince", "birthUpto");
    private ContactService contactService = ServiceFactory.getContactService();

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("searching contacts");
        Map<String, String> params = new HashMap<String, String>();
        for (String param: parametersList) {
            String value = request.getParameter(param).trim();
            if (StringUtils.isNotEmpty(value)) {
                params.put(param, value);
            }
        }
        List<Contact> contacts = contactService.searchContacts(params);

        int contactsCount = contacts.size();
        request.setAttribute("contactsCount", contactsCount);
        request.setAttribute("contact", contacts);
        return "/show.jspx";

    }
}
