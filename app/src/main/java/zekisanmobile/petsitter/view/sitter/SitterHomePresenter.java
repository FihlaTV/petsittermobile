package zekisanmobile.petsitter.view.sitter;

import java.util.List;

import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

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
