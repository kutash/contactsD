package command;

import DAO.*;
import model.Address;
import model.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by Galina on 14.03.2017.
 */
public class SaveCommand implements Command {



    public String execute(HttpServletRequest request, HttpServletResponse response) {

        Long id;
        String idSt = request.getParameter("idContact");
        if (!idSt.equals("")){
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
        Contact contact = new Contact(id, firstName, middleName, lastName, birthday, sex, citizenship, status, site, email, company, address1);

        DAO contactDAO = DAOFactory.getContactDao();
        long id2 = contactDAO.setContact(contact);

        /*try {
            savePhoto(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }*/

        return "/my-servlet?command=show";
    }


    /*public void savePhoto(long id) throws IOException, ServletException {
        System.out.println("hello1");
        String savePath = "D:/h";
        File fileSaveDir = new File(savePath);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Part filePart = request.getPart("avatar");
        if(filePart.getSize()>0) {
            String fileExtension = filePart.getName().substring(filePart.getName().indexOf('.'));
            savePath = fileSaveDir.getAbsolutePath()+ File.separator + id + fileExtension;
            filePart.write(savePath);
        }
        System.out.println("hello2");
        contactDAO.setPhoto(id, savePath);
    }*/

}
