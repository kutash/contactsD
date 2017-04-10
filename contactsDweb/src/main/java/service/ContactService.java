package service;

import DAO.*;
import command.ErrorCommand;
import model.Address;
import model.Attachment;
import model.Contact;
import model.Phone;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Galina on 24.03.2017.
 */
public class ContactService {

    DAO contactDAO = DAOFactory.getContactDao();


    public Long setContact(Contact contact){
        long id2 = contactDAO.setContact(contact);
        return id2;
    }

    public Contact getById(Long id){
        Contact contact = contactDAO.getById(id);
        return contact;
    }

    public List<Contact> getCont(int page) {
        List<Contact> contacts = null;
        try {
            contacts = contactDAO.getCont(page);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return contacts;
    }

    public int getContactsCount() {
        int count = 0;
        try {
            count = contactDAO.getContactsCount();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return count;
    }

    public String getPhoto(long idContact) {
        return contactDAO.getPhoto(idContact);
    }

    public void setPhoto(long idContact, String path) {
        contactDAO.setPhoto(idContact,path);
    }

    public void deleteContact(Long id) {
        contactDAO.deleteContact(id);
    }

    public void deleteAddress(Long idAddress){contactDAO.deleteAddress(idAddress);}

    public List<Attachment> getAttaches(Long idContact){
        return contactDAO.getAttaches(idContact);
    }

    public void setAttaches(Long idContact, List<Attachment> attachList){
        contactDAO.setAttaches(idContact, attachList);
    }

    public Attachment getAttach(Long idContact){
       return contactDAO.getAttach(idContact);
    }

    public void deleteAttachment(Long idContact){
        contactDAO.deleteAttachment(idContact);
    }

    public List<Phone> getPhones(Long idContact){
        List<Phone> listPhones = contactDAO.getPhones(idContact);
        return listPhones;
    }

    public void deletePhones(Long idContact){
        contactDAO.deletePhones(idContact);
    }


    public void setPhone(long idContact, List<Phone> phones){
        contactDAO.setPhones(idContact, phones);
    }



    public List<Phone> makePhone(HttpServletRequest request, Long idContact){
        List<Phone> phones = new ArrayList<Phone>();
        Enumeration<String> paramNames = request.getParameterNames();

        Pattern p = Pattern.compile("telephone\\d+");
        while(paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            Matcher m = p.matcher(paramName);

            if (m.matches()) {

                long i = Long.parseLong(paramName.substring(9));

                String countryCode = request.getParameter("countryCode"+i);
                System.out.println(countryCode);
                String operatorCode = request.getParameter("operatorCode"+i);
                String telephone = request.getParameter("telephone"+i);
                String type = request.getParameter("type"+i);
                String comment = request.getParameter("comment"+i);
                Phone phone = new Phone(countryCode, operatorCode, telephone, type, comment);

                if (idContact != null)
                    phone.setContactId(idContact);
                phones.add(phone);
            }
        }

        return phones;
    }


    public List<Contact> searchContacts(Map<String, String> params,int page){
        List<Contact> contacts = contactDAO.searchContacts(params, page);
        return contacts;
    }

    public int countForSearch(Map<String, String> params){
       int count = contactDAO.countForSearch(params);
       return count;
    }



    public Contact makeContact(HttpServletRequest request){
        Long id;
        String idSt = request.getParameter("idContact");
        if (StringUtils.isNotEmpty(idSt)) {
            id = Long.parseLong(idSt);

        } else {
            id = null;
        }
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String sex = request.getParameter("sex");
        if (sex.equals("")){
            sex=null;
        }
        String citizenship = request.getParameter("citizenship");
        String status = request.getParameter("status");
        if (status.equals("")){
            status=null;
        }
        String site = request.getParameter("site");
        String email = request.getParameter("email");
        String company = request.getParameter("company");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String flat = request.getParameter("flat");
        String index = request.getParameter("index");
        String date = request.getParameter("birthday");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            java.util.Date birthdayUtil = format.parse(date);
            birthday = new Date(birthdayUtil.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Address address1 = new Address(country, city, street, house, flat, index);
        Contact contact = new Contact(id, firstName, middleName, lastName, birthday, citizenship, sex, status, site, email, company, address1);
        return contact;
    }




    public Contact validateAndMake(HttpServletRequest request){
        Long id;
        String idSt = request.getParameter("idContact");
        if (StringUtils.isNotEmpty(idSt)) {
            id = Long.parseLong(idSt);

        } else {
            id = null;
        }
        String firstName = request.getParameter("firstName").trim();
        String middleName = request.getParameter("middleName").trim();
        String lastName = request.getParameter("lastName").trim();

        String sex = request.getParameter("sex").trim();
        if (sex.equals("")){
             sex = null;
        }
        String citizenship = request.getParameter("citizenship").trim();
        String status = request.getParameter("status");
        if (status.equals("")){
            status=null;
        }
        String site = request.getParameter("site").trim();
        String email = request.getParameter("email").trim();
        String company = request.getParameter("company").trim();
        String country = request.getParameter("country").trim();
        String city = request.getParameter("city").trim();
        String street = request.getParameter("street").trim();
        String house = request.getParameter("house");
        String flat = request.getParameter("flat");
        String index = request.getParameter("index").trim();
        String date = request.getParameter("birthday").trim();


        Pattern patternNull = Pattern.compile("");
        Pattern patternName = Pattern.compile("([a-z]|[A-Z]|[а-я]|[А-Я])*");
        Pattern patternDate = Pattern.compile("(?:19[0-9]{2}|20[0-1]{1}[0-7]{1})-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))");
        Pattern patternEmail = Pattern.compile("^[-\\w.]+\\+*+[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$");
        Pattern patternSite = Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
        Pattern patternIndex = Pattern.compile("\\d*");
        Matcher m1 = patternName.matcher(firstName);
        Matcher m2 = patternName.matcher(middleName);
        Matcher m22 = patternNull.matcher(middleName);
        Matcher m3 = patternName.matcher(lastName);
        Matcher m4 = patternName.matcher(citizenship);
        Matcher m5 = patternName.matcher(company);
        Matcher m55 = patternNull.matcher(company);
        Matcher m6 = patternName.matcher(street);
        Matcher m7 = patternName.matcher(country);
        Matcher m8 = patternName.matcher(city);
        Matcher m9 = patternDate.matcher(date);
        Matcher m99 = patternNull.matcher(date);
        Matcher m10 = patternEmail.matcher(email);
        Matcher m11 = patternSite.matcher(site);
        Matcher m111 = patternNull.matcher(site);
        Matcher m12 = patternIndex.matcher(index);
        Matcher m13 = patternIndex.matcher(house);
        Matcher m14 = patternIndex.matcher(flat);


        if (m1.matches() && (m2.matches() || m22.matches()) && m3.matches() && m4.matches() && (m5.matches() || m55.matches()) && m6.matches() && m7.matches() && m8.matches() && (m9.matches() || m99.matches()) && m10.matches() && (m11.matches() || m111.matches()) && m12.matches() && m13.matches() && m14.matches()) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
            try {
                java.util.Date birthdayUtil = format.parse(date);
                birthday = new Date(birthdayUtil.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Address address1 = new Address(country, city, street, house, flat, index);
            Contact contact = new Contact(id, firstName, middleName, lastName, birthday, citizenship, sex, status, site, email, company, address1);
            return contact;
        } else {
            return null;
        }
    }



    public List<Contact> getContactsForBirthday(){
       List<Contact> birthContacts = contactDAO.getContactsForBirthday();
       return birthContacts;
    }
}
