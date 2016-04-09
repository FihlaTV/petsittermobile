package zekisanmobile.petsitter.Owner;

import java.util.ArrayList;

import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;

public class MyPetSittersPresenterImpl implements MyPetSittersPresenter {

    private MyPetSittersView view;
    private User user;

    public MyPetSittersPresenterImpl(MyPetSittersView view){
        this.view = view;
        this.user = UserDAO.getLoggedUser(0);
    }

    @Override
    public User getLoggedUser() {
        return this.user;
    }

    @Override
    public ArrayList<Contact> getContacts() {
        return ContactDAO.getAllContactsFromOwner(this.user.getOwner().getApiId());
    }
}
