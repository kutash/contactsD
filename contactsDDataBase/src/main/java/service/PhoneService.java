package service;

import DAO.PhoneDao;
import DAO.PhoneDaoFactory;
import model.Phone;
import java.util.List;

public class PhoneService {

    private PhoneDao phoneDAO = PhoneDaoFactory.getPhoneDao();

    public List<Phone> getPhones(Long idContact){
        return phoneDAO.getPhones(idContact);
    }

    public void deletePhones(String idContact){

        phoneDAO.deletePhones(idContact);
    }

    public void setPhone(String idContact, List<Phone> phones){

        phoneDAO.setPhones(idContact, phones);
    }
}
