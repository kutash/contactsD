package command;

import DAO.*;
import model.Address;
import model.Attachment;
import model.Contact;
import org.apache.commons.io.FileUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;


/**
 * Created by Galina on 14.03.2017.
 */
public class SaveCommand implements Command {

    DAO contactDAO = DAOFactory.getContactDao();
    private HttpServletRequest request;
    private HttpSession session;


    public String execute(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        session = request.getSession();
        Long id;
        String idSt = request.getParameter("idContact");
        if (!idSt.equals("")) {
            id = Long.parseLong(idSt);

        } else {
            id = null;
        }
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            java.util.Date birthdayUtil = format.parse(request.getParameter("birthday"));
            birthday = new Date(birthdayUtil.getTime());
        } catch (ParseException e) {

        }
        String sex = request.getParameter("sex");
        String citizenship = request.getParameter("citizenship");
        String status = request.getParameter("status");
        String site = request.getParameter("site");
        String email = request.getParameter("email");
        String company = request.getParameter("company");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String index = request.getParameter("index");
        Address address1 = new Address(country, city, address, index);
        Contact contact = new Contact(id, firstName, middleName, lastName, birthday, citizenship, sex, status, site, email, company, address1);


        long id2 = contactDAO.setContact(contact);

        try {
            savePhoto(id2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "/my-servlet?command=show";
    }



    public void savePhoto(long id) throws IOException, ServletException {

        Properties properties = new Properties();
        properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        String photoPath = properties.getProperty("AVATARS_PATH");


        File fileSaveDir = new File(photoPath);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }


        Part photoPart = request.getPart("photo");

        String fileName = "";
        String contentDisp = photoPart.getHeader("Content-Disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        fileName = fileName.substring(fileName.indexOf("."));


        if (photoPart.getSize() > 0) {
            photoPath += File.separator + id + fileName;
            photoPart.write(photoPath);
        } else {

        }

            contactDAO.setPhoto(id, photoPath);
    }


    private void saveAttaches(long id) throws IOException {

    }
}
