package mailing;

import command.SendEmailCommand;
import model.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import service.ServiceFactory;
import utils.EmailSender;
import javax.mail.Session;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BirthdayMailing {

    private Logger logger = LogManager.getLogger(BirthdayMailing.class);
    private ContactService contactService = ServiceFactory.getContactService();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startService() {
        scheduler.scheduleAtFixedRate(this::sendEmail, 0, 1, TimeUnit.DAYS);
    }

    public void stopService() {
        scheduler.shutdown();
    }

    private void sendEmail(){
        logger.info("sending birthday email");
        try {
            final Properties properties = new Properties();
            properties.load(SendEmailCommand.class.getResourceAsStream("/email.properties"));
            String letter = makeLetter();
            String theme = "Дни рождения";
            String address = properties.getProperty("ADMIN-EMAIL");
            final String sender = properties.getProperty("mail.user.name");
            final String password = properties.getProperty("mail.user.password");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(sender, password);
                        }
                    });

            new EmailSender().sendEmail(address, theme, letter, properties, sender, session);

        } catch (IOException e) {
            logger.error("error while sending birthday emails");
        }
    }

    private String makeLetter(){
        List<Contact> contacts = contactService.getContactsForBirthday();
        String letter;
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
}
