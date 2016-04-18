package zekisanmobile.petsitter.model;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.Sort;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;

public class OwnerModel {

    @Inject
    Realm realm;

    public OwnerModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void save(Owner owner){
        owner.validate();
        realm.beginTransaction();
        realm.copyToRealm(owner);
        realm.commitTransaction();
    }

    public void saveAll(final List<Owner> owners){
        ValidationUtil.pruneInvalid(owners);
        if (owners.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(owners);
        realm.commitTransaction();
    }

    public Owner find(long id) {
        return realm.where(Owner.class).equalTo("id", id).findFirst();
    }

    public List<Owner> all(){
        return realm.where(Owner.class).findAll();
    }

    public Owner getLoggedOwnerUser() {
        return realm.where(Owner.class).equalTo("user.logged", true)
                .equalTo("user.type", 0).findFirst();
    }

    public List<Contact> getCurrentContacts(long id){
        return realm.where(Contact.class)
                .equalTo("owner.id", id)
                .equalTo("status", 30)
                .greaterThanOrEqualTo("dateFinal", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public List<Contact> getNewContacts(long id){
        return realm.where(Contact.class)
                .equalTo("owner.id", id)
                .equalTo("status", 10)
                .greaterThanOrEqualTo("dateStart", new Date())
                .findAllSorted("dateStart", Sort.DESCENDING);
    }

    public List<Contact> getFinishedContacts(long id) {
        return realm.where(Contact.class)
                .equalTo("owner.id", id)
                .equalTo("status", 40)
                .findAllSorted("dateFinal", Sort.DESCENDING);
    }

    public List<Contact> getRejectContacts(long id) {
        return realm.where(Contact.class)
                .equalTo("owner.id", id)
                .equalTo("status", 20)
                .findAllSorted("dateFinal", Sort.DESCENDING);
    }
}
