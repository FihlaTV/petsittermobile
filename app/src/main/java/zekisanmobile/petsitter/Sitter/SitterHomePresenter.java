package zekisanmobile.petsitter.Sitter;

import java.util.List;

import zekisanmobile.petsitter.model.Contact;
import zekisanmobile.petsitter.model.Sitter;

public interface SitterHomePresenter {

    void onDestroy();
    void getContacts();
    void updateContacts();

    String getLoggedUserName();
    String getLoggedUserPhoto();
    String getLoggedUserEmail();
    String getStringLoggedUserSitterApiId();

    long getLoggedUserSitterApiId();
    long getLoggedUserSitterId();

    Sitter getSitterFromUser();

    List<Contact> getNewContacts();
    List<Contact> getCurrentContacts();

}
