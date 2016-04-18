package zekisanmobile.petsitter.model;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Contact;

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
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(contacts);
        realm.commitTransaction();
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
