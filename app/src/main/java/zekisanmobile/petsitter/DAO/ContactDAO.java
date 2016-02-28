package zekisanmobile.petsitter.DAO;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Contact;

public class ContactDAO {

    public static RealmResults<Contact> getAllContacts(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Contact.class).findAll();
    }

}
