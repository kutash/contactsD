package service;

import DAO.ContactDao;
import DAO.ContactDaoFactory;
import model.Contact;
import java.util.List;
import java.util.Map;


public class ContactService {

    private ContactDao contactDAO = ContactDaoFactory.getContactDao();

    public Long setContact(Contact contact){
        return contactDAO.setContact(contact);
    }

    public Contact getById(Long id){
        return contactDAO.getById(id);
    }

    public List<Contact> getCont(int page) {

        return contactDAO.getContacts(page);
    }

    public int getContactsCount() {
        int count;
        count = contactDAO.getContactsCount();
        return count;
    }

    public String getPhoto(long idContact) {
        return contactDAO.getPhoto(idContact);
    }

    public void setPhoto(long idContact, String path) {

        contactDAO.setPhoto(idContact,path);
    }

    public void deleteContact(String contacts) {
        contactDAO.deleteContact(contacts);
    }

    public List<Contact> searchContacts(Map<String, String> params,int page){
        return contactDAO.searchContacts(params, page);
    }

    public int countForSearch(Map<String, String> params){
       return contactDAO.countForSearch(params);
    }

    public List<Contact> getContactsForBirthday(){
       return contactDAO.getContactsForBirthday();
    }


}
