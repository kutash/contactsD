package builder;

import model.Address;
import model.Contact;
import model.Phone;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Builder {

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

        Address address1 = new Address();
        address1.setCountry(country);
        address1.setCity(city);
        address1.setStreet(street);
        address1.setHouse(house);
        address1.setFlat(flat);
        address1.setIndex(index);

        Contact contact = new Contact();
        contact.setId(id);
        contact.setFirstName(firstName);
        contact.setMiddleName(middleName);
        contact.setLastName(lastName);
        contact.setSex(sex);
        contact.setCitizen(citizenship);
        contact.setSite(site);
        contact.setStatus(status);
        contact.setCompany(company);
        contact.setEmail(email);
        contact.setBirthday(birthday);
        contact.setAddress(address1);
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
        String house = request.getParameter("house").trim();
        String flat = request.getParameter("flat").trim();
        String index = request.getParameter("index").trim();
        String date = request.getParameter("birthday").trim();

        Pattern patternNull = Pattern.compile("");
        Pattern patternFlat = Pattern.compile("\\d*([a-z]|[A-Z])?");
        Pattern patternName = Pattern.compile("([a-z]|[A-Z]|[а-я]|[А-Я])+");
        Pattern patternDate = Pattern.compile("(?:19[0-9]{2}|20[0-1]{1}[0-7]{1})-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))");
        Pattern patternEmail = Pattern.compile("^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$");
        Pattern patternSite = Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
        Pattern patternIndex = Pattern.compile("\\d*");
        Pattern patternCompany = Pattern.compile("([a-z]|[A-Z]|[а-я]|[А-Я])+(\\s)?([a-z]|[A-Z]|[а-я]|[А-Я])*");
        Matcher m1 = patternName.matcher(firstName);
        Matcher m2 = patternName.matcher(middleName);
        Matcher m22 = patternNull.matcher(middleName);
        Matcher m3 = patternName.matcher(lastName);
        Matcher m4 = patternName.matcher(citizenship);
        Matcher m5 = patternNull.matcher(company);
        Matcher m55 = patternCompany.matcher(company);
        Matcher m6 = patternName.matcher(street);
        Matcher m7 = patternName.matcher(country);
        Matcher m8 = patternName.matcher(city);
        Matcher m9 = patternDate.matcher(date);
        Matcher m99 = patternNull.matcher(date);
        Matcher m10 = patternEmail.matcher(email);
        Matcher m11 = patternSite.matcher(site);
        Matcher m111 = patternNull.matcher(site);
        Matcher m12 = patternIndex.matcher(index);
        Matcher m13 = patternFlat.matcher(house);
        Matcher m14 = patternFlat.matcher(flat);

        if (m1.matches() && (m2.matches() || m22.matches()) && m3.matches() && m4.matches() && (m5.matches() || m55.matches()) && m6.matches() && m7.matches() && m8.matches() && (m9.matches() || m99.matches()) && m10.matches() && (m11.matches() || m111.matches()) && m12.matches() && m13.matches() && m14.matches()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
            try {
                java.util.Date birthdayUtil = format.parse(date);
                birthday = new Date(birthdayUtil.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Address address1 = new Address();
            address1.setCountry(country);
            address1.setCity(city);
            address1.setStreet(street);
            address1.setHouse(house);
            address1.setFlat(flat);
            address1.setIndex(index);

            Contact contact = new Contact();
            contact.setId(id);
            contact.setFirstName(firstName);
            contact.setMiddleName(middleName);
            contact.setLastName(lastName);
            contact.setSex(sex);
            contact.setCitizen(citizenship);
            contact.setSite(site);
            contact.setStatus(status);
            contact.setCompany(company);
            contact.setEmail(email);
            contact.setBirthday(birthday);
            contact.setAddress(address1);
            return contact;
        } else {
            return null;
        }
    }

}
