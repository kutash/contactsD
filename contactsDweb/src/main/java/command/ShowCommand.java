package command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paginator.Paginator;
import service.ContactService;
import service.ServiceFactory;
import java.util.List;
import java.util.Map;


/**
 * Created by Galina on 14.03.2017.
 */
public class ShowCommand implements Command {

    private Logger logger = LogManager.getLogger(ShowCommand.class);
    private ContactService contactService = ServiceFactory.getContactService();
    HttpSession session;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("showing contacts");
        Boolean isSearch = false;
        session = request.getSession();
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");
        session.removeAttribute("emailContacts");
        Map<String, String> params = (Map<String, String>) session.getAttribute("params");
        List<Contact> contacts;
        int targetPage = 1;
        int pagesCount = 0;
        int contactsCount = 0;
        isSearch = (Boolean) session.getAttribute("isSearch");
        if (isSearch == null) {
            isSearch = false;
        }
        if (!isSearch) {
            contactsCount = contactService.getContactsCount();
            pagesCount = (int) Math.ceil(contactsCount / 10.0);
        } else if (isSearch) {
            contactsCount = contactService.countForSearch(params);
            pagesCount = (int) Math.ceil(contactsCount / 10.0);
        }

        String targetPageParam = request.getParameter("targetPage");
        if (targetPageParam != null) {
            if (targetPageParam.equals("next")) {
                targetPage = ((Integer) request.getSession().getAttribute("CURRENT_PAGE")) + 1;
                if (targetPage > pagesCount) {
                    targetPage = pagesCount;
                }
            } else if (targetPageParam.equals("prev")) {
                targetPage = ((Integer) request.getSession().getAttribute("CURRENT_PAGE") - 1);
                if (targetPage < 1) {
                    targetPage = 1;
                }
            } else {
                try {
                    targetPage = Integer.parseInt(targetPageParam);
                } catch (NumberFormatException e) {
                    logger.error("Target page is not number! Unable to parse.");
                    return "error";
                }
                if (targetPage < 1 || (targetPage > pagesCount && pagesCount != 0)) {
                    logger.error("Target page {} out of range!", targetPage);
                    return "error";
                }
            }

        }
        if (!isSearch) {
            contacts = contactService.getCont(targetPage);

        } else {
            contacts = contactService.searchContacts(params, targetPage);
        }

        request.setAttribute("CONTACTS_COUNT", contactsCount);
        request.setAttribute("PAGINATOR", Paginator.getPaginator(targetPage, pagesCount));
        request.getSession().setAttribute("CURRENT_PAGE", targetPage);
        request.getSession().setAttribute("contact", contacts);

        return "/show.jspx";
    }
}
