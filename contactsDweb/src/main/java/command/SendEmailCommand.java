package command;

import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Galina on 02.04.2017.
 */
public class SendEmailCommand implements Command {

    private Logger logger = LogManager.getLogger(SendEmailCommand.class);
    HttpSession session;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        session=request.getSession();
        List<Contact> emailContact = (List<Contact>) session.getAttribute("emailContacts");
        session.removeAttribute("emailContacts");
        String[] addresses = request.getParameterValues("whom");
        if(addresses==null){
            return "/error.jspx";
        }
        logger.info("sending emails to contacts {}", Arrays.toString(addresses));
        Pattern patternEmail = Pattern.compile("^[-._'a-z0-9]+\\+?+[-._'a-z0-9]+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$");
        for (String address : addresses){
            Matcher m = patternEmail.matcher(address);
            if (!m.matches()){
                return "/error.jspx";
            }
        }


        String theme = request.getParameter("theme");
        String text = request.getParameter("letter");


        final Properties properties = new Properties();
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


            if (emailContact==null){
                for (String address : addresses){
                    sendEmail(address, theme, text, properties, sender, session);
                }
            }else {
                ST st = null;
                for (String address : addresses) {
                    for (Contact contact : emailContact) {
                        if (contact.getEmail().equals(address)) {
                            st = new ST(text);
                            st.add("contact", contact);
                            sendEmail(address, theme, st.render(), properties, sender, session);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        request.setAttribute("message","Mail successfully sent");
        return "/my-servlet?command=show";
    }



    private void sendEmail(String address, String theme, String text, Properties properties, String sender, Session session){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, properties.getProperty("ADMIN-NAME")));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(theme);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
