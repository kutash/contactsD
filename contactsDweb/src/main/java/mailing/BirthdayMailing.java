package mailing;


import command.SendEmailCommand;
import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import service.ContactService;
import service.ServiceFactory;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Galina on 05.04.2017.
 */
public class BirthdayMailing {

    private Logger logger = LogManager.getLogger(BirthdayMailing.class);
    private ContactService contactService = ServiceFactory.getContactService();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startService() {
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                try {
                    sendEmail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    public void stopService() {
        scheduler.shutdown();
    }


    private void sendEmail(){
        logger.info("sending birthday email");
        String letter = makeLetter();
        String theme = "Дни рождения";
        String address = "kutashgalina16@gmail.com";


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


                sendEmail(address, theme, letter, properties, sender, session);

        } catch (IOException e) {
            logger.error("error while sending birthday emails");
        }
    }



    private String makeLetter(){
        List<Contact> contacts = contactService.getContactsForBirthday();
        String letter = "";
        if (contacts.size()==0){
            letter = "Некого поздравлять.";
        } else {
            letter="Сегодня дни рождения у: ";
            int count = 1;
            for (Contact contact : contacts) {

                letter +=count+")"+contact.getFullName()+" ";
                count++;
            }
        }
        return letter;
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
