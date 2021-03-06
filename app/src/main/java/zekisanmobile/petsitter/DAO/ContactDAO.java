package zekisanmobile.petsitter.DAO;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Animal;
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
                                                String time_final, String created_at, Sitter sitter, Owner owner,
                                                double totalValue, int status, List<Animal> animals){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Contact newContact;

        if((newContact = findContact(id)) == null) {
            newContact = realm.createObject(Contact.class);
            long newiD = getAllContacts().last().getId();
            newContact.setId(newiD + 1);
        }

        newContact.setId(id);
        newContact.setDate_start(date_start);
        newContact.setDate_final(date_final);
        newContact.setTime_start(time_start);
        newContact.setTime_final(time_final);
        newContact.setCreated_at(created_at);
        newContact.setSitter(sitter);
        newContact.setOwner(owner);
        newContact.setTotalValue(totalValue);
        newContact.setStatus(status);
        if (newContact.getAnimals().size() > 0) newContact.getAnimals().clear();
        newContact.getAnimals().addAll(animals);

        realm.commitTransaction();
        realm.close();
        return newContact;
    }

    public static ArrayList<Contact> getAllContactsFromSitter(long apiId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contactsFromRealm = realm.where(Contact.class).equalTo("sitter.apiId", apiId).findAll();
        return convertRealmResultsToList(contactsFromRealm);
    }

    public static ArrayList<Contact> getNewContactsFromSitter(long apiId){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contactsFromRealm = realm.where(Contact.class)
                .equalTo("sitter.apiId", apiId)
                .equalTo("status", 10)
                .greaterThanOrEqualTo("date_start", new Date())
                .findAll();
        return convertRealmResultsToList(contactsFromRealm);
    }

    public static ArrayList<Contact> getCurrentContactsFromSitter(long apiId){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contactsFromRealm = realm.where(Contact.class)
                .equalTo("sitter.apiId", apiId)
                .equalTo("status", 30)
                .greaterThanOrEqualTo("date_final", new Date())
                .findAll();
        return convertRealmResultsToList(contactsFromRealm);
    }

    @NonNull
    private static ArrayList<Contact> convertRealmResultsToList(RealmResults<Contact> contactsFromRealm) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for(int i = 0; i < contactsFromRealm.size(); i++){
            contacts.add(contactsFromRealm.get(i));
        }
        return contacts;
    }

    public static Contact getContact(long id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Contact.class).equalTo("id", id).findFirst();
    }

    public static void deleteContact(long id){
        Realm realm = Realm.getDefaultInstance();
        realm.where(Contact.class).equalTo("id", id).findFirst().removeFromRealm();
    }

    public static void updateStatus(long id, int status) {
        Realm realm = Realm.getDefaultInstance();
        Contact contact = realm.where(Contact.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        contact.setStatus(status);
        realm.commitTransaction();
    }

    public static ArrayList<Contact> getAllContactsFromOwner(long apiId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contactsFromRealm = realm.where(Contact.class).equalTo("owner.apiId", apiId).findAll();
        return convertRealmResultsToList(contactsFromRealm);
    }
}
