package zekisanmobile.petsitter.Sitter;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Contact;

public interface SitterHomeView {

    void updateAdapters(List<Contact> newContacts, List<Contact> currentContacts);

}
