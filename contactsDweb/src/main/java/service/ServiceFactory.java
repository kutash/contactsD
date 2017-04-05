package service;

/**
 * Created by Galina on 24.03.2017.
 */
public class ServiceFactory {

        public static ContactService getContactService(){
            return new ContactService();
        }

}

