package DAO;

import model.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAO {

    List<Contact> getContacts(int page) throws SQLException;

    int getContactsCount() throws SQLException;

    Long setAddress(Contact contact);

    Long setContact(Contact contact);

    Long saveContact(Contact contact);

    void editContact(Contact contact);

    Contact getById(Long id);

    String getPhoto(long idContact);

    void setPhoto(long idContact, String path);

    void deleteContact(String contacts);

    void saveAttaches(Attachment attachment);

    void deleteAddress(String idAddress);

    List<Attachment> getAttaches(Long idContact);

    void setAttaches(String idContact, List<Attachment> attachList);

    //Attachment getAttach(Long idContact);

    void deleteAttachment(String idContact);

    void savePhone(Phone phone);

    void setPhones(String idContact, List<Phone> phones);

    List<Phone> getPhones(Long idContact);

    void deletePhones(String idContact);

    List<Contact> searchContacts(Map<String, String> params, int page);

    int countForSearch(Map<String, String> params);

    List<Contact> getContactsForBirthday();
}
