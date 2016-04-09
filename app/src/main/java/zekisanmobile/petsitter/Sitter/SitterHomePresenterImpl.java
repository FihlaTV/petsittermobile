package zekisanmobile.petsitter.Sitter;

import java.util.ArrayList;

import zekisanmobile.petsitter.Handlers.GetSitterContactsHandler;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;

public class SitterHomePresenterImpl implements SitterHomePresenter{

    SitterHomeView view;
    private User user;

    public SitterHomePresenterImpl(SitterHomeView view){
        this.view = view;
        this.user = UserDAO.getLoggedUser(1);
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
        return user.getName();
    }

    @Override
    public String getLoggedUserPhoto() {
        return user.getPhoto();
    }

    @Override
    public String getLoggedUserEmail() {
        return user.getEmail();
    }

    @Override
    public String getStringLoggedUserSitterApiId() {
        return String.valueOf(getLoggedUserSitterApiId());
    }

    @Override
    public long getLoggedUserSitterApiId() {
        return getSitterFromUser().getApiId();
    }

    @Override
    public Sitter getSitterFromUser() {
        return user.getSitter();
    }

    @Override
    public ArrayList<Contact> getNewContacts() {
        return ContactDAO.getNewContactsFromSitter(getLoggedUserSitterApiId());
    }

    @Override
    public ArrayList<Contact> getCurrentContacts() {
        return ContactDAO.getCurrentContactsFromSitter(getLoggedUserSitterApiId());
    }
}
