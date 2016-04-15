package zekisanmobile.petsitter.Owner;

import java.util.List;

import zekisanmobile.petsitter.model.Contact;
import zekisanmobile.petsitter.model.User;

public interface MyPetSittersPresenter {

    User getLoggedUser();

    List<Contact> getContacts();

}
