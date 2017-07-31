package command;

import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import utils.EmailSender;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendEmailCommand implements Command {

    private Logger logger = LogManager.getLogger(SendEmailCommand.class);

    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Properties properties = new Properties();
        HttpSession httpsession=request.getSession();
        List<Contact> emailContact;
        try {
            emailContact = (List<Contact>) httpsession.getAttribute("emailContacts");
        } catch (ClassCastException e){
            throw new CommandException("Exception in casting types",e);
        }
        httpsession.removeAttribute("emailContacts");
        String[] addresses = request.getParameterValues("whom");
        if(addresses == null){
            return "/error.jspx";
        }
        logger.info("sending emails to contacts {}", Arrays.toString(addresses));
        Pattern patternEmail = Pattern.compile("^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$");
        for (String address : addresses){
            Matcher m = patternEmail.matcher(address);
            if (!m.matches()){
                return "/error.jspx";
            }
        }
        String theme = request.getParameter("theme");
        String text = request.getParameter("letter");
        try {
            properties.load(SendEmailCommand.class.getResourceAsStream("/email.properties"));
            final String sender = properties.getProperty("mail.user.name");
            final String password = properties.getProperty("mail.user.password");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(sender, password);
                        }
                    });

            if (emailContact == null){
                for (String address : addresses){
                    new EmailSender().sendEmail(address, theme, text, properties, sender, session);
                }
            }else {
                ST st;
                for (String address : addresses) {
                    for (Contact contact : emailContact) {
                        if (contact.getEmail().equals(address)) {
                            st = new ST(text);
                            st.add("contact", contact);
                            new EmailSender().sendEmail(address, theme, st.render(), properties, sender, session);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new CommandException("Exception while preparing messages", e);
        }
        request.setAttribute("message","Mail successfully sent");
        return "/my-servlet?command=show";
    }
}
