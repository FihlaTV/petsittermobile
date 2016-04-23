package zekisanmobile.petsitter.model;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

public class SitterModel {

    public SitterModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void save(Realm realm, Sitter sitter) {
        sitter.validate();
        realm.beginTransaction();
        realm.copyToRealm(sitter);
        realm.commitTransaction();
    }

    public void saveAll(Realm realm, final List<Sitter> sitters) {
        ValidationUtil.pruneInvalid(sitters);
        if (sitters.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(sitters);
        realm.commitTransaction();
    }

    public Sitter findByApiId(Realm realm, long apiId) {
        return realm.where(Sitter.class).equalTo("apiId", apiId).findFirst();
    }

    public Sitter find(Realm realm, long id) {
        return realm.where(Sitter.class).equalTo("id", id).findFirst();
    }

    public List<Sitter> all(Realm realm) {
        return realm.where(Sitter.class).findAll();
    }

    public List<Contact> getNextContacts(Realm realm, long id) {
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 30)
                .greaterThan("dateStart", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public List<Contact> getFinishedContacts(Realm realm, long id) {
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 40)
                .findAll();
    }

    public List<Contact> getNewContacts(Realm realm, long id){
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 10)
                .greaterThanOrEqualTo("dateStart", new Date())
                .findAll();
    }

    public List<Contact> getCurrentContacts(Realm realm, long id){
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 30)
                .lessThanOrEqualTo("dateStart", new Date())
                .greaterThanOrEqualTo("dateFinal", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public Sitter getLoggedSitterUser(Realm realm) {
        return realm.where(Sitter.class).equalTo("user.logged", true)
                .equalTo("user.category", 1).findFirst();
    }
}
