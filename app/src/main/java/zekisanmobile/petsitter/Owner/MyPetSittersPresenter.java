package zekisanmobile.petsitter.Owner;

import java.util.List;

import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Owner;

public interface MyPetSittersPresenter {

    Owner getLoggedUser();

    List<Contact> getContacts();

}
