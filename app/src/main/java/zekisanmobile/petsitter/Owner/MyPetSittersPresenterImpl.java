package zekisanmobile.petsitter.Owner;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Owner;
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
        return getLoggedUser().owner.getContacts();
    }
}
