package DAO;

import model.*;
import java.util.List;
import java.util.Map;

public interface ContactDao {

    List<Contact> getContacts(int page);

    int getContactsCount();

    Long setContact(Contact contact);

    Long saveContact(Contact contact);

    void editContact(Contact contact);

    Contact getById(Long id);

    String getPhoto(long idContact);

    void setPhoto(long idContact, String path);

    void deleteContact(String contacts);

    List<Contact> searchContacts(Map<String, String> params, int page);

    int countForSearch(Map<String, String> params);

    List<Contact> getContactsForBirthday();
}
