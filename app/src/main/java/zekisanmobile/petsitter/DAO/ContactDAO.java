package zekisanmobile.petsitter.DAO;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Owner;
import zekisanmobile.petsitter.Model.Sitter;

public class ContactDAO {

    public static RealmResults<Contact> getAllContacts(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Contact.class).findAll();
    }

    public static Contact findContact(long id){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Contact.class).equalTo("id", id).findFirst();
    }

    public static Contact insertOrUpdateContact(long id, Date date_start, Date date_final, String time_start,
                                               String time_final, Sitter sitter, Owner owner){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Contact newContact;

        if((newContact = findContact(id)) == null) {
            newContact = realm.createObject(Contact.class);
            newContact.setId(getAllContacts().size() + 1);
        }

        newContact.setId(id);
        newContact.setDate_start(date_start);
        newContact.setDate_final(date_final);
        newContact.setTime_start(time_start);
        newContact.setTime_final(time_final);
        newContact.setSitter(sitter);
        newContact.setOwner(owner);

        realm.commitTransaction();

        return newContact;
    }

    public static ArrayList<Contact> getAllContactsFromSitter(long apiId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contactsFromRealm = realm.where(Contact.class).equalTo("sitter_id", apiId).findAll();
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for(int i = 0; i < contacts.size(); i++){
            contacts.add(contactsFromRealm.get(i));
        }
        return contacts;
    }
}
