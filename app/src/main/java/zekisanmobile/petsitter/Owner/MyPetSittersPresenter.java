package zekisanmobile.petsitter.Owner;

import java.util.List;

import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;

public interface MyPetSittersPresenter {

    User getLoggedUser();

    List<Contact> getContacts();

}
