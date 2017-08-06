package service;

public class ContactServiceFactory {

        public static ContactService getContactService(){
            return new ContactService();
        }
}

