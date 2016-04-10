package zekisanmobile.petsitter.Sitter;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Handlers.GetSitterContactsHandler;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;

public class SitterHomePresenterImpl implements SitterHomePresenter{

    SitterHomeView view;
    private User user;

    public SitterHomePresenterImpl(SitterHomeView view){
        this.view = view;
        this.user = User.getLoggedUser(1);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getContacts() {
        new GetSitterContactsHandler(this).execute(getStringLoggedUserSitterApiId());
    }

    @Override
    public void updateContacts() {
        if (view != null){
            view.updateAdapters(getNewContacts(), getCurrentContacts());
        }
    }

    @Override
    public String getLoggedUserName() {
        return user.name;
    }

    @Override
    public String getLoggedUserPhoto() {
        return user.photo;
    }

    @Override
    public String getLoggedUserEmail() {
        return user.email;
    }

    @Override
    public String getStringLoggedUserSitterApiId() {
        return String.valueOf(getLoggedUserSitterApiId());
    }

    @Override
    public long getLoggedUserSitterApiId() {
        return getSitterFromUser().apiId;
    }

    @Override
    public Sitter getSitterFromUser() {
        return user.sitter;
    }

    @Override
    public List<Contact> getNewContacts() {
        return new ArrayList<Contact>(Sitter.getNewContacts(getSitterFromUser().getId()));
    }

    @Override
    public List<Contact> getCurrentContacts() {
        return new ArrayList<Contact>(Sitter.getCurrentContacts(getSitterFromUser().getId()));
    }
}
