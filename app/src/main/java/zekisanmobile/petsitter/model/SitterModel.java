package zekisanmobile.petsitter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.Sort;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.Formatter;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

public class SitterModel {

    @Inject
    Realm realm;

    public SitterModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void save(Sitter sitter) {
        sitter.validate();
        realm.beginTransaction();
        realm.copyToRealm(sitter);
        realm.commitTransaction();
    }

    public void saveAll(final List<Sitter> sitters) {
        ValidationUtil.pruneInvalid(sitters);
        if (sitters.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(sitters);
        realm.commitTransaction();
    }

    public Sitter findByApiId(long apiId) {
        return realm.where(Sitter.class).equalTo("apiId", apiId).findFirst();
    }

    public Sitter find(long id) {
        return realm.where(Sitter.class).equalTo("id", id).findFirst();
    }

    public List<Sitter> all() {
        return realm.where(Sitter.class).findAll();
    }

    public List<Contact> getNextContacts(long id) {
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 30)
                .lessThan("dateStart", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public List<Contact> getFinishedContacts(long id) {
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 40)
                .findAll();
    }

    public List<Contact> getNewContacts(long id){
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 10)
                .greaterThanOrEqualTo("dateStart", new Date())
                .findAll();
    }

    public List<Contact> getCurrentContacts(long id){
        return realm.where(Contact.class)
                .equalTo("sitter.id", id)
                .equalTo("status", 30)
                .greaterThanOrEqualTo("dateFinal", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public Sitter getLoggedSitterUser() {
        return realm.where(Sitter.class).equalTo("user.logged", true)
                .equalTo("user.type", 1).findFirst();
    }
}
