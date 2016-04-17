package zekisanmobile.petsitter.view.sitter;

import java.util.List;

import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.vo.Contact;

public interface SitterHomeView {

    void updateAdapters(List<Contact> newContacts, List<Contact> currentContacts);

    PetSitterApp getPetSitterApp();
}
