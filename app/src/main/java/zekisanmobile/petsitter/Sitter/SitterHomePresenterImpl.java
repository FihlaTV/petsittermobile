package zekisanmobile.petsitter.Sitter;

import java.util.ArrayList;

import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Handlers.GetContactsHandler;
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
        new GetContactsHandler(this).execute(getStringLoggedUserSitterApiId());
    }

    @Override
    public void updateContacts() {
        if (view != null){
            ArrayList<Contact> contacts = ContactDAO.getAllContactsFromSitter(getLoggedUserSitterApiId());
            view.updateAdapter(contacts);
        }
    }

    @Override
    public void onItemClicked(int position) {

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
}
