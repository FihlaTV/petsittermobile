package zekisanmobile.petsitter.model;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.Sitter;

public class ContactModel {

    @Inject
    Realm realm;

    public ContactModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void save(Contact contact){
        contact.validate();
        realm.beginTransaction();
        realm.copyToRealm(contact);
        realm.commitTransaction();
    }

    public void saveAll(final List<Contact> contacts){
        ValidationUtil.pruneInvalid(contacts);
        if (contacts.isEmpty()) {
            return;
        }

        for (Contact contact : contacts) {
            realm.beginTransaction();
            Contact contactFromDB;
            if ((contactFromDB = realm.where(Contact.class).equalTo("apiId", contact.getApiId())
                    .findFirst()) == null) {
                contactFromDB = realm.createObject(Contact.class);
                long id = realm.where(Contact.class).max("id").longValue() + 1;
                contactFromDB.setId(id);
            }
            contactFromDB.setDateStart(contact.getDateStart());
            contactFromDB.setDateFinal(contact.getDateFinal());
            contactFromDB.setTimeStart(contact.getTimeStart());
            contactFromDB.setTimeFinal(contact.getTimeFinal());
            contactFromDB.setCreatedAt(contact.getCreatedAt());
            realm.commitTransaction();
        }
    }

    public void insertOrUpdateContact(final long id, final long apiId, final Date date_start,
                                      final Date date_final, final String time_start,
                                      final String time_final, final String created_at,
                                      final Sitter sitter, final Owner owner,
                                      final double totalValue, final int status,
                                      final List<Animal> animals){
        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Contact newContact;
                if((newContact = realm.where(Contact.class).equalTo("apiId", id).findFirst()) == null) {
                    newContact = realm.createObject(Contact.class);
                    long newId = realm.where(Contact.class).max("id").longValue() + 1;
                    newContact.setId(newId + 1);
                }
                newContact.setApiId(apiId);
                newContact.setDateStart(date_start);
                newContact.setDateFinal(date_final);
                newContact.setTimeStart(time_start);
                newContact.setTimeFinal(time_final);
                newContact.setCreatedAt(created_at);
                newContact.setSitter(realm.where(Sitter.class)
                        .equalTo("id", sitter.getId()).findFirst());
                newContact.setOwner(realm.where(Owner.class)
                        .equalTo("apiId", owner.getApiId()).findFirst());
                newContact.setTotalValue(totalValue);
                newContact.setStatus(status);
                if (newContact.getAnimals().size() > 0) newContact.getAnimals().clear();
                newContact.getAnimals().addAll(animals);
            }
        });
    }

    public Contact find(long id) {
        return realm.where(Contact.class).equalTo("id", id).findFirst();
    }

    public List<Contact> all(){
        return realm.where(Contact.class).findAll();
    }

    public void updateStatus(long apiId, int status) {
        Contact contact = realm.where(Contact.class).equalTo("apiId", apiId).findFirst();
        realm.beginTransaction();
        contact.setStatus(status);
        realm.commitTransaction();
    }

    public void delete(long id) {
        Contact contact = realm.where(Contact.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        contact.removeFromRealm();
        realm.commitTransaction();
    }
}
