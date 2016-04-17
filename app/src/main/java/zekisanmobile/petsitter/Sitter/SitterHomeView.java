package zekisanmobile.petsitter.Sitter;

import java.util.List;

import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.model.Contact;

public interface SitterHomeView {

    void updateAdapters(List<Contact> newContacts, List<Contact> currentContacts);

    PetSitterApp getPetSitterApp();
}
