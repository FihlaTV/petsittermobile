package zekisanmobile.petsitter.Sitter;

import zekisanmobile.petsitter.Model.Sitter;

public interface SitterHomePresenter {

    void onDestroy();
    void getContacts();
    void updateContacts();
    void onItemClicked(int position);

    String getLoggedUserName();
    String getLoggedUserPhoto();
    String getLoggedUserEmail();
    String getStringLoggedUserSitterApiId();

    long getLoggedUserSitterApiId();

    Sitter getSitterFromUser();

}
