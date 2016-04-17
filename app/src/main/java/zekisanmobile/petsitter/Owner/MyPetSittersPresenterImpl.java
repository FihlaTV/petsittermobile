package zekisanmobile.petsitter.Owner;

import java.util.List;

import javax.inject.Inject;

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
        List<Contact> allContacts = ownerModel.getCurrentContacts(owner.getId());
        allContacts.addAll(ownerModel.getNewContacts(owner.getId()));
        allContacts.addAll(ownerModel.getFinishedContacts(owner.getId()));
        allContacts.addAll(ownerModel.getRejectContacts(owner.getId()));
        return allContacts;
    }
}
