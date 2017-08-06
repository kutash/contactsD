package DAO;

import model.Phone;
import java.util.List;

public interface PhoneDao {

    void savePhone(Phone phone);

    void setPhones(String idContact, List<Phone> phones);

    List<Phone> getPhones(Long idContact);

    void deletePhones(String idContact);
}
