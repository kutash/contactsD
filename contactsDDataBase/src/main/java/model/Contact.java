package model;

import java.util.Date;
import java.util.List;

/**
 * Created by Galina on 10.03.2017.
 */
public class Contact {
     private Long id;
     private String firstName;
     private String middleName;
     private String lastName;
     private Date birthday;
     private Address address;
     private String company;
     private String sex;
     private String citizenship;
     private String status;
     private String site;
     private String email;
     private String photo;
     private List<Phone> phoneList;

     public Contact(Long id, String firstName, String middleName, String lastName, Date birthday, Address address, String company){
         this.id=id;
         this.firstName=firstName;
         this.middleName=middleName;
         this.lastName=lastName;
         this.birthday=birthday;
         this.address=address;
         this.company=company;

    }

    public Contact(Long id, String firstName, String middleName, String lastName, Date birthday, String citizenship, String sex, String status, String photo, String site, String email, String company, Address address){

        this.id = id;
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
        this.birthday=birthday;
        this.citizenship=citizenship;
        this.sex=sex;
        this.status=status;
        this.photo=photo;
        this.site=site;
        this.email=email;
        this.company=company;
        this.address=address;

    }

    public Contact(Long id, String firstName, String middleName, String lastName, Date birthday, String citizenship, String sex, String status, String site, String email, String company, Address address){

        this.id=id;
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
        this.birthday=birthday;
        this.sex=sex;
        this.citizenship=citizenship;
        this.status=status;
        //this.photo=photo;
        this.site=site;
        this.email=email;
        this.company=company;
        this.address=address;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizen() {
        return citizenship;
    }

    public void setCitizen(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhot() {
        return photo;
    }

    public void setPhoto(String foto) {
        this.photo = foto;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", address=" + address +
                ", company='" + company + '\'' +
                ", sex='" + sex + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", status='" + status + '\'' +
                ", site='" + site + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", phoneList=" + phoneList +
                '}';
    }

    public String getFullName(){
         return lastName+" "+firstName+" "+middleName;
    }
}
