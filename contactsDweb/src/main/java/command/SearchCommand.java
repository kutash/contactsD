package command;

import model.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Galina on 14.03.2017.
 */
public class SearchCommand implements Command {

    private Logger logger = LogManager.getLogger(SearchCommand.class);
    private List<String> parametersList = Arrays.asList("firstName", "lastName", "middleName", "sex", "status",
            "citizenship", "country", "city", "street", "house", "flat", "birthSince", "birthUpto");
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

        String s = request.getParameter("currentPage");
        int targetPage = 0;
        if (s!= null){
            targetPage = Integer.parseInt(s);

        }else {
            targetPage = 1;
        }
        List<Contact> contacts = contactService.searchContacts(params);


        int contactsCount = contacts.size();
        int pagesCount = (int) Math.ceil(contactsCount / 10.0);
        request.setAttribute("contact", contacts);
        request.setAttribute("pages", pagesCount);
        request.setAttribute("currentPage", targetPage);
        //request.setAttribute("contactsCount", contactsCount);

        return "/show.jspx";

    }
}
