package DAO;

import model.*;

import java.sql.SQLException;
import java.util.List;

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

    //long countContacts(SearchCriteria criteria);

    void deleteContact(Long idContact);

    //List<Contact> getToShowContacts(SearchCriteria criteria, ViewSettings settings);

    void insertPhone(Phone phone);

    void setPhones(long idContact, List<Phone> phones);

    List<Phone> getPhones(Long idContact);

    //List<Attach> getAttaches(Long idContact);

    //void insertAttach(Attach attach);

    //void setAttaches(long idContact,Collection<Attach> attaches);

    List<Contact> forBirthdayContacts();
}
