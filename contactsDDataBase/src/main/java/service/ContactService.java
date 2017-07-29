package service;

import DAO.DAO;
import DAO.DAOException;
import DAO.DAOFactory;
import model.Attachment;
import model.Contact;
import model.Phone;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class ContactService {

    private DAO contactDAO = DAOFactory.getContactDao();

    public Long setContact(Contact contact){
        return contactDAO.setContact(contact);
    }

    public Contact getById(Long id){
        return contactDAO.getById(id);
    }

    public List<Contact> getCont(int page) {
        List<Contact> contacts;
        try {
            contacts = contactDAO.getContacts(page);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return contacts;
    }

    public int getContactsCount() {
        int count;
        try {
            count = contactDAO.getContactsCount();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
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

    public void deleteAddress(String idAddress){
        contactDAO.deleteAddress(idAddress);}

    public List<Attachment> getAttaches(Long idContact){
        return contactDAO.getAttaches(idContact);
    }

    public void setAttaches(String idContact, List<Attachment> attachList){
        contactDAO.setAttaches(idContact, attachList);
    }

    /*public Attachment getAttach(Long idContact){

        return contactDAO.getAttach(idContact);
    }*/

    public void deleteAttachment(String idContact){

        contactDAO.deleteAttachment(idContact);
    }

    public List<Phone> getPhones(Long idContact){
        return contactDAO.getPhones(idContact);
    }

    public void deletePhones(String idContact){

        contactDAO.deletePhones(idContact);
    }

    public void setPhone(String idContact, List<Phone> phones){

        contactDAO.setPhones(idContact, phones);
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

    public void saveAddress(Contact contact) {
        contactDAO.setAddress(contact);
    }
}
