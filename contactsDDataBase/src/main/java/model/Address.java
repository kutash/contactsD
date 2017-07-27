package model;

public class Address {
    private Long addressId;
    private Long contactId;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
    private String index;

    public Address(){}

    public Address(Long addressId, String country, String city, String street, String house, String flat, String index){
        this.addressId=addressId;
        this.country=country;
        this.city=city;
        this.street=street;
        this.house=house;
        this.flat=flat;
        this.index=index;
    }

    public Address(String country, String city, String street, String house, String flat, String index){
        this.country=country;
        this.city=city;
        this.street=street;
        this.house=house;
        this.flat=flat;
        this.index=index;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getHouse() {

        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return country + " " + city + " " +
                street + "/" +house+"/"+flat+" "+
                index;
    }
}
