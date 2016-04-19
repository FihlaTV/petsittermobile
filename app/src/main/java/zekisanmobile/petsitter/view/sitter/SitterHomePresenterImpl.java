package zekisanmobile.petsitter.view.sitter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

public class SitterHomePresenterImpl implements SitterHomePresenter{

    SitterHomeView view;
    private Sitter sitter;

    @Inject
    SitterModel sitterModel;

    public SitterHomePresenterImpl(SitterHomeView view){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.sitter = sitterModel.getLoggedSitterUser();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getContacts() {
        //new GetSitterContactsHandler(this, view).execute(getStringLoggedUserSitterApiId());
    }

    @Override
    public void updateContacts() {
        if (view != null){
            view.updateAdapters(getNewContacts(), getCurrentContacts());
        }
    }

    @Override
    public String getLoggedUserName() {
        return sitter.getName();
    }

    @Override
    public String getLoggedUserPhoto() {
        return sitter.getPhoto();
    }

    @Override
    public String getLoggedUserEmail() {
        return sitter.getUser().getEmail();
    }

    @Override
    public String getStringLoggedUserSitterId() {
        return String.valueOf(getLoggedUserSitterId());
    }

    @Override
    public long getLoggedUserSitterId() {
        return this.sitter.getId();
    }

    @Override
    public Sitter getSitterFromUser() {
        return sitter;
    }

    @Override
    public List<Contact> getNewContacts() {
        return new ArrayList<Contact>(sitterModel.getNewContacts(getSitterFromUser().getId()));
    }

    @Override
    public List<Contact> getCurrentContacts() {
        return new ArrayList<Contact>(sitterModel.getCurrentContacts(getSitterFromUser().getId()));
    }
}
