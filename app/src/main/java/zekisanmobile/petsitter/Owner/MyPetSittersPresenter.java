package zekisanmobile.petsitter.Owner;

import java.util.ArrayList;

import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;

public interface MyPetSittersPresenter {

    User getLoggedUser();

    ArrayList<Contact> getContacts();

}
