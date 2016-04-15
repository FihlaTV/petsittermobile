package zekisanmobile.petsitter.Sitter;

import java.util.List;

import zekisanmobile.petsitter.model.Contact;

public interface OtherContactsPresenter {

    List<Contact> getContacts(String type);

    String getTitle(String contactsType);

}
