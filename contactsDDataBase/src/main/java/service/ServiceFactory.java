package service;

public class ServiceFactory {

        public static ContactService getContactService(){

            return new ContactService();
        }
}

