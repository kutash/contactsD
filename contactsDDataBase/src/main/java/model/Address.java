package model;

/**
 * Created by Galina on 10.03.2017.
 */
public class Address {
    private Long addressId;
    private String country;
    private String city;
    private String address;
    private String index;

    public Address(String country, String city, String address, String index){
        this.country=country;
        this.city=city;
        this.address=address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return country + " " + city + " " +
                address + " " +
                index;
    }
}
