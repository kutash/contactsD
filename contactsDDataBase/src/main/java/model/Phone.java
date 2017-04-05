package model;

/**
 * Created by Galina on 10.03.2017.
 */
public class Phone {

    private Long phoneId;
    private Long contactId;
    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private String phoneType;
    private String comment;

    public Phone(Long phoneId, String countryCode, String operatorCode, String phoneNumber, String phoneType, String comment) {
        this.phoneId = phoneId;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.comment = comment;
    }

    public Phone(String countryCode, String operatorCode, String phoneNumber, String phoneType, String comment) {
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.comment = comment;
    }

    public Phone(Long phoneId, String countryCode, String operatorCode, String phoneNumber, String phoneType, String comment, Long contactId) {
        this.phoneId=phoneId;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.comment = comment;
        this.contactId=contactId;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + phoneId +
                ", countryCode='" + countryCode + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneType='" + phoneType + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getFullNumber(){
        return countryCode+" ("+operatorCode+") "+phoneNumber;
    }


}
