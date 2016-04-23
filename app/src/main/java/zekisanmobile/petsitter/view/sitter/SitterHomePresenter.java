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
    String getStringLoggedUserSitterId();

    long getLoggedUserSitterId();

    Sitter getSitterFromUser();

    List<Contact> getNewContacts(long sitter_id);
    List<Contact> getCurrentContacts(long sitter_id);

}
