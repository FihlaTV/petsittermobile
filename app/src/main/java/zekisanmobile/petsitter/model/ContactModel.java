package zekisanmobile.petsitter.model;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.OwnerComment;
import zekisanmobile.petsitter.vo.Rate;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.vo.SitterComment;

public class ContactModel {

    public ContactModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void save(Realm realm, Contact contact) {
        contact.validate();
        realm.beginTransaction();
        realm.copyToRealm(contact);
        realm.commitTransaction();
    }

    public void saveAll(Realm realm, final List<Contact> contacts) {
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

    public long insertOrUpdateContact(Realm realm, final long id, final long apiId, final Date date_start,
                                      final Date date_final, final String time_start,
                                      final String time_final, final String created_at,
                                      final Sitter sitter, final Owner owner,
                                      final double totalValue, final int status,
                                      final List<Animal> animals, @Nullable final Rate rate,
                                      final boolean fromApi) {

        final long[] contact_id = new long[1];
        realm.beginTransaction();
        Contact newContact;

        if ((newContact = realm.where(Contact.class).equalTo("id", id).findFirst()) == null) {
            newContact = realm.createObject(Contact.class);
            long newId = fromApi ? id : realm.where(Contact.class).max("id").longValue() + 1;
            newContact.setId(newId);
        }

        newContact.setApiId(apiId);
        newContact.setDateStart(date_start);
        newContact.setDateFinal(date_final);
        newContact.setTimeStart(time_start);
        newContact.setTimeFinal(time_final);
        newContact.setCreatedAt(created_at);
        newContact.setSitter(realm.where(Sitter.class).equalTo("id", sitter.getId())
                .findFirst());
        newContact.setOwner(realm.where(Owner.class).equalTo("apiId", owner
                .getApiId()).findFirst());
        newContact.setTotalValue(totalValue);
        newContact.setStatus(status);

        if (newContact.getAnimals().size() > 0) newContact.getAnimals().clear();
        newContact.getAnimals().addAll(animals);

        contact_id[0] = newContact.getId();
        realm.commitTransaction();

        if (rate != null) {
            Rate newRate;
            realm.beginTransaction();
            newRate = new Rate();
            newRate.setId(rate.getId());
            newRate.setPositive(rate.isPositive());
            realm.copyToRealmOrUpdate(newRate);
            realm.commitTransaction();

            if (rate.getOwnerComment() != null) {
                OwnerComment newOwnerComment;
                realm.beginTransaction();
                newOwnerComment = new OwnerComment();
                newOwnerComment.setId(rate.getOwnerComment().getId());
                newOwnerComment.setText(rate.getOwnerComment().getText());
                realm.copyToRealmOrUpdate(newOwnerComment);
                realm.commitTransaction();
                realm.beginTransaction();
                Rate rateWithOwnerComment = realm.where(Rate.class)
                        .equalTo("id", newRate.getId()).findFirst();
                rateWithOwnerComment.setOwnerComment(realm.where(OwnerComment.class)
                        .equalTo("id", newOwnerComment.getId()).findFirst());
                realm.commitTransaction();
            }
            if (rate.getSitterComment() != null) {
                SitterComment newSitterComment;
                realm.beginTransaction();
                newSitterComment = new SitterComment();
                newSitterComment.setId(rate.getSitterComment().getId());
                newSitterComment.setText(rate.getSitterComment().getText());
                realm.copyToRealmOrUpdate(newSitterComment);
                realm.commitTransaction();
                realm.beginTransaction();
                Rate rateWithSitterComment = realm.where(Rate.class)
                        .equalTo("id", newRate.getId()).findFirst();
                rateWithSitterComment.setSitterComment(realm.where(SitterComment.class)
                        .equalTo("id", newSitterComment.getId()).findFirst());
                realm.commitTransaction();
            }
            realm.beginTransaction();
            newContact.setRate(realm.where(Rate.class).equalTo("id", newRate.getId()).findFirst());
            realm.commitTransaction();
        }
        return contact_id[0];
    }

    public Contact find(Realm realm, long id) {
        return realm.where(Contact.class).equalTo("id", id).findFirst();
    }

    public List<Contact> all(Realm realm) {
        return realm.where(Contact.class).findAll();
    }

    public void updateStatus(Realm realm, long apiId, int status) {
        Contact contact = realm.where(Contact.class).equalTo("apiId", apiId).findFirst();
        realm.beginTransaction();
        contact.setStatus(status);
        realm.commitTransaction();
    }

    public void delete(Realm realm, long id) {
        Contact contact = realm.where(Contact.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        contact.removeFromRealm();
        realm.commitTransaction();
    }

    public void rateContact(Realm realm, long contact_id) {
        Contact contact = realm.where(Contact.class).equalTo("id", contact_id).findFirst();
    }
}
