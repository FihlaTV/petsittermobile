package zekisanmobile.petsitter.view.owner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmList;
import io.realm.RealmResults;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;

public class MyPetSittersPresenterImpl implements MyPetSittersPresenter {

    private MyPetSittersView view;
    private Owner owner;

    @Inject
    OwnerModel ownerModel;

    public MyPetSittersPresenterImpl(MyPetSittersView view){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.owner = ownerModel.getLoggedOwnerUser();
    }

    @Override
    public Owner getLoggedUser() {
        return this.owner;
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> allContacts = new ArrayList<>();

        for (Contact contact : ownerModel.getCurrentContacts(owner.getId())) {
            allContacts.add(contact);
        }

        for (Contact contact : ownerModel.getNewContacts(owner.getId())) {
            allContacts.add(contact);
        }

        for (Contact contact : ownerModel.getFinishedContacts(owner.getId())) {
            allContacts.add(contact);
        }

        for (Contact contact : ownerModel.getRejectContacts(owner.getId())) {
            allContacts.add(contact);
        }
        return allContacts;
    }
}
