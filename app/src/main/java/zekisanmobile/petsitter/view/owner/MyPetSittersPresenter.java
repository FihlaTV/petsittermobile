package zekisanmobile.petsitter.view.owner;

import java.util.List;

import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;

public interface MyPetSittersPresenter {

    Owner getLoggedUser();

    List<Contact> getContacts();

}
