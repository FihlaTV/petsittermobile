package zekisanmobile.petsitter.Owner;

import java.util.List;

import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;

public class MyPetSittersPresenterImpl implements MyPetSittersPresenter {

    private MyPetSittersView view;
    private User user;

    public MyPetSittersPresenterImpl(MyPetSittersView view){
        this.view = view;
        this.user = User.getLoggedUser(0);
    }

    @Override
    public User getLoggedUser() {
        return this.user;
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> allContacts = getLoggedUser().owner.getCurrentContacts(this.user.owner.getId());
        allContacts.addAll(getLoggedUser().owner.getNewContacts(this.user.owner.getId()));
        allContacts.addAll(getLoggedUser().owner.getFinishedContacts(this.user.owner.getId()));
        allContacts.addAll(getLoggedUser().owner.getRejectContacts(this.user.owner.getId()));
        return allContacts;
    }
}
