package utils;

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
        List<Phone> phones = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();

        Pattern p = Pattern.compile("telephone\\d+");
        while(paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            Matcher m = p.matcher(paramName);
            if (m.matches()) {
                long i = Long.parseLong(paramName.substring(9));
                String countryCode = request.getParameter("countryCode"+i);
                String operatorCode = request.getParameter("operatorCode"+i);
                String number = request.getParameter("telephone"+i);
                String type = request.getParameter("type"+i);
                String comment = request.getParameter("comment"+i);

                Phone phone = new Phone();
                phone.setComment(comment);
                phone.setCountryCode(countryCode);
                phone.setOperatorCode(operatorCode);
                phone.setPhoneNumber(number);
                phone.setPhoneType(type);

                if (idContact != null)
                    phone.setContactId(idContact);
                phones.add(phone);
            }
        }
        return phones;
    }

    public Contact makeContact(HttpServletRequest request){
        Long id;
        Long idAddress;
        String idAddressSt = request.getParameter("idAddress");
        if (StringUtils.isNotEmpty(idAddressSt)) {
            idAddress = Long.parseLong(idAddressSt);
        } else {
            idAddress = null;
        }
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
        java.util.Date birthday = parseDate(date);

        Address address1 = new Address();
        address1.setCountry(country);
        address1.setCity(city);
        address1.setStreet(street);
        address1.setHouse(house);
        address1.setFlat(flat);
        address1.setIndex(index);
        address1.setAddressId(idAddress);

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

    public Map<String, String> validateAndMake(Contact contact){

        Pattern patternNull = Pattern.compile("");
        Pattern patternFlat = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-\\.\\/]{1,45}$");
        Pattern patternName = Pattern.compile("([a-z]|[A-Z]|[а-я]|[А-Я])+");
        Pattern patternEmail = Pattern.compile("^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$");
        Pattern patternSite = Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
        Pattern patternIndex = Pattern.compile("\\d{1,45}");
        Pattern patternCompany = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.\\s]{0,45}$");
        /*Matcher m1 = patternName.matcher(firstName);
        Matcher m2 = patternName.matcher(middleName);
        Matcher m22 = patternNull.matcher(middleName);
        Matcher m3 = patternName.matcher(lastName);
        Matcher m4 = patternName.matcher(citizenship);*/
        Matcher companyMatcher = patternCompany.matcher(contact.getCompany());
        /*Matcher m6 = patternName.matcher(street);
        Matcher m7 = patternName.matcher(country);
        Matcher m8 = patternName.matcher(city);*/
        Matcher emailMathcer = patternEmail.matcher(contact.getEmail());
        /*Matcher m11 = patternSite.matcher(site);
        Matcher m111 = patternNull.matcher(site);
        Matcher m12 = patternIndex.matcher(index);
        Matcher m13 = patternFlat.matcher(house);
        Matcher m14 = patternFlat.matcher(flat);*/

        Map<String, String> map = new HashMap<>();
        if (!companyMatcher.matches()){
            map.put("company", "Allowable characters: letters, digits, spaces, dash, underscore!");
        }
        if (!emailMathcer.matches()){
            map.put("email", "Invalid email!");
        }
        return map;

    }

    private boolean checkBirthday(java.util.Date birthday) {
        java.util.Date now = new java.util.Date();
        return !birthday.after(now);
    }

    private java.util.Date parseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday;
        if (StringUtils.isNotEmpty(date)) {
            try {
                java.util.Date birthdayUtil = format.parse(date);
                birthday = new Date(birthdayUtil.getTime());
            } catch (ParseException e) {
                throw new UtilException("Exception in parsing date",e);
            }
        } else {
            birthday = null;
        }
        return birthday;
    }

}
