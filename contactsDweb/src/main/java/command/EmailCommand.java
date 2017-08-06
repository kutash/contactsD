package command;

import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import service.ContactService;
import service.ContactServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class EmailCommand implements Command {

    private Logger logger = LogManager.getLogger(EmailCommand.class);
    private ContactService contactService = ContactServiceFactory.getContactService();

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("attaches");
        session.removeAttribute("temp_photo_path");
        session.removeAttribute("isSearch");
        STGroup group = new STGroupFile("emailTemplates.stg");
        List<String> templates = new ArrayList<>();
        ST st1 = group.getInstanceOf("template1");
        ST st2 = group.getInstanceOf("template2");
        templates.add(st1.impl.getTemplateSource());
        templates.add(st2.impl.getTemplateSource());
        request.setAttribute("templates", templates);
        String [] chosen = request.getParameterValues("idContact");
        if (chosen==null || chosen[0].equals("")){
            request.setAttribute("contacts", null);
            session.setAttribute("contacts", null);
        }else {
            List<Contact> emailContact = new ArrayList<>();
            for (String c : chosen) {
                long id = Long.parseLong(c);
                Contact contact = contactService.getById(id);
                emailContact.add(contact);
            }
            request.setAttribute("emailContacts", emailContact);
            session.setAttribute("emailContacts", emailContact);
            logger.info("filling email form for contacts {}", Arrays.toString(chosen));
        }
        return "/email.jspx";
    }
}
