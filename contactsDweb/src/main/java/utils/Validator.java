package utils;

import model.Contact;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public Map<String, String> validateContact(Contact contact){

        Pattern patternFlatHouse = Pattern.compile("^[0-9][a-zA-Z0-9/]*");
        Pattern patternName = Pattern.compile("([a-z]|[A-Z]|[а-я]|[А-Я]*)+");
        Pattern patternEmail = Pattern.compile("^([-._'a-z0-9])+(\\+)?([-._'a-z0-9])+@(?:[a-z0-9][-a-z0-9]+\\.)+[a-z]{2,6}$");
        Pattern patternSite = Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
        Pattern patternIndex = Pattern.compile("\\d*");
        Pattern patternCompany = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_.\\s]*$");
        Pattern patternCountryCity = Pattern.compile("^[а-яА-ЯёЁa-zA-Z]+\\s*[а-яА-ЯёЁa-zA-Z]*-?\\s*[а-яА-ЯёЁa-zA-Z]*$");
        Pattern patternStreet = Pattern.compile("^[а-яА-ЯёЁa-zA-Z0-9]+\\s*[а-яА-ЯёЁa-zA-Z0-9]*-?\\.?\\s*/*[а-яА-ЯёЁa-zA-Z0-9]*$");

        Matcher firstNameMatcher = patternName.matcher(contact.getFirstName());
        Matcher lastNameMatcher = patternName.matcher(contact.getLastName());
        Matcher middleNameMatcher = patternName.matcher(contact.getMiddleName());
        Matcher citizenshipMatcher = patternName.matcher(contact.getCitizen());
        Matcher companyMatcher = patternCompany.matcher(contact.getCompany());
        Matcher siteMatcher = patternSite.matcher(contact.getSite());
        Matcher emailMatcher = patternEmail.matcher(contact.getEmail());
        Matcher countryMatcher = patternCountryCity.matcher(contact.getAddress().getCountry());
        Matcher cityMatcher = patternCountryCity.matcher(contact.getAddress().getCity());
        Matcher streetMatcher = patternStreet.matcher(contact.getAddress().getStreet());
        Matcher houseMatcher = patternFlatHouse.matcher(contact.getAddress().getHouse());
        Matcher flatMatcher = patternFlatHouse.matcher(contact.getAddress().getFlat());
        Matcher indexMatcher = patternIndex.matcher(contact.getAddress().getIndex());

        Map<String, String> map = new HashMap<>();
        if (!firstNameMatcher.matches()){
            map.put("firstName", "This field must contain only letters");
        }
        if (contact.getFirstName().toCharArray().length > 44){
            map.put("firstNameSize", "Maximum 45 characters");
        }
        if (StringUtils.isEmpty(contact.getFirstName())){
            map.put("firstNameBlank", "This field cannot be blank");
        }
        if (!lastNameMatcher.matches()){
            map.put("lastName", "This field must contain only letters");
        }
        if (contact.getLastName().toCharArray().length > 44){
            map.put("lastNameSize", "Maximum 45 characters");
        }
        if (StringUtils.isEmpty(contact.getLastName())){
            map.put("lastNameBlank", "This field cannot be blank");
        }
        if (!middleNameMatcher.matches()){
            map.put("middleName", "This field must contain only letters");
        }
        if (contact.getMiddleName().toCharArray().length > 44){
            map.put("middleNameSize", "Maximum 45 characters");
        }
        if (contact.getBirthday() != null) {
            if (!checkBirthday(contact.getBirthday()))
                map.put("birthday", "Wrong date format");
        }
        if (!citizenshipMatcher.matches()){
            map.put("citizenship", "This field must contain only letters");
        }
        if (contact.getCitizen().toCharArray().length > 44){
            map.put("citizenshipSize", "Maximum 45 characters");
        }
        if (StringUtils.isEmpty(contact.getCitizen())){
            map.put("citizenshipBlank", "This field cannot be blank");
        }
        if (StringUtils.isNotEmpty(contact.getSite())) {
            if (!siteMatcher.matches()) {
                map.put("site", "Invalid website!");
            }
            if (contact.getSite().toCharArray().length > 44){
                map.put("siteSize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getEmail())) {
            map.put("emailBlank", "This field cannot be blank");
        }else {
            if (!emailMatcher.matches()) {
                map.put("email", "Invalid email!");
            }
            if (contact.getEmail().toCharArray().length > 89) {
                map.put("emailSize", "Maximum 90 characters");
            }
        }
        if (StringUtils.isNotEmpty(contact.getCompany())) {
            if (!companyMatcher.matches()) {
                map.put("company", "Allowable characters: letters, digits, spaces, dash, underscore!");
            }
            if (contact.getCompany().toCharArray().length > 44) {
                map.put("companySize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getCountry())){
            map.put("countryBlank", "This field cannot be blank");
        }else {
            if (!countryMatcher.matches()) {
                map.put("country", "Allowable characters: letters, space, dash!");
            }
            if (contact.getAddress().getCountry().toCharArray().length > 44) {
                map.put("countrySize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getCity())){
            map.put("cityBlank", "This field cannot be blank");
        } else {
            if (!cityMatcher.matches()) {
                map.put("city", "Allowable characters: letters, space, dash!");
            }
            if (contact.getAddress().getCity().toCharArray().length > 44) {
                map.put("citySize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getStreet())){
            map.put("streetBlank", "This field cannot be blank");
        } else {
            if (!streetMatcher.matches()) {
                map.put("street", "Allowable characters: letters, digits, spaces, dash, dot, slash!");
            }
            if (contact.getAddress().getStreet().toCharArray().length > 44) {
                map.put("streetSize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getHouse())){
            map.put("houseBlank", "This field cannot be blank");
        } else {
            if (!houseMatcher.matches()) {
                map.put("house", "Allowable characters: letters, digits, slash!");
            }
            if (contact.getAddress().getHouse().toCharArray().length > 44) {
                map.put("houseSize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getFlat())){
            map.put("flatBlank", "This field cannot be blank");
        } else {
            if (!flatMatcher.matches()) {
                map.put("flat", "Allowable characters: letters, digits, slash!");
            }
            if (contact.getAddress().getFlat().toCharArray().length > 44) {
                map.put("flatSize", "Maximum 45 characters");
            }
        }
        if (StringUtils.isEmpty(contact.getAddress().getIndex())){
            map.put("indexBlank", "This field cannot be blank");
        } else {
            if (!indexMatcher.matches()) {
                map.put("index", "This field must contain only digits!");
            }
            if (contact.getAddress().getIndex().toCharArray().length > 44) {
                map.put("indexSize", "Maximum 45 characters");
            }
        }
        return map;
    }

    private boolean checkBirthday(java.util.Date birthday) {
        java.util.Date now = new java.util.Date();
        return !birthday.after(now);
    }
}
