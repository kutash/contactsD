package command;

import DAO.*;
import model.Address;
import model.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by Galina on 14.03.2017.
 */
public class SaveCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {

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
        Contact contact = new Contact(firstName, middleName, lastName, birthday, sex, citizenship, status, site, email, company, address1);

        DAO contactDAO = DAOFactory.getContactDao();
        contactDAO.setContact(contact);

        return "/my-servlet?command=show";
    }
}
