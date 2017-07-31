package utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public void sendEmail(String address, String theme, String text, Properties properties, String sender, Session session){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, properties.getProperty("ADMIN-NAME")));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(theme);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            throw new UtilException("Exception while sending email",e);
        }
    }
}
