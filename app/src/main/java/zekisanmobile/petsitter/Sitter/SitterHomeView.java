package zekisanmobile.petsitter.Sitter;

import java.util.ArrayList;

import zekisanmobile.petsitter.Model.Contact;

public interface SitterHomeView {

    void updateAdapters(ArrayList<Contact> newContacts, ArrayList<Contact> currentContacts);

}
