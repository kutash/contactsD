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

    void deleteContact(Long idContact);

    void saveAttaches(Attachment attachment);




}
