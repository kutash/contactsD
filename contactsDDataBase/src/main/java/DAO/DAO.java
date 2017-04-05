package DAO;

import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Galina on 10.03.2017.
 */
public interface DAO {

    List<Contact> getCont(int page) throws SQLException;

    int getContactsCount() throws SQLException;

    Long setAddress(Contact contact);

    Long setContact(Contact contact);

    Long saveContact(Contact contact);

    void editContact(Contact contact);

    Contact getById(Long id);

    String getPhoto(long idContact);

    void setPhoto(long idContact, String path);

    void deleteContact(Long idContact);

    void saveAttaches(Attachment attachment);

    void deleteAddress(Long idAddress);

    List<Attachment> getAttaches(Long idContact);

    void setAttaches(Long idContact, List<Attachment> attachList);

    Attachment getAttach(Long idContact);

    void deleteAttachment(Long idContact);

    void savePhone(Phone phone);

    void setPhones(long idContact, List<Phone> phones);

    List<Phone> getPhones(Long idContact);

    void deletePhones(Long idContact);

    List<Contact> searchContacts(Map<String, String> params);

    List<Contact> getContactsForBirthday();
}
