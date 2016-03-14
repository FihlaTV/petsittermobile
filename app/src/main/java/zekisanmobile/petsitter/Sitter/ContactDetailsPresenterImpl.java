package zekisanmobile.petsitter.Sitter;

import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.Model.Contact;

public class ContactDetailsPresenterImpl implements ContactDetailsPresenter {

    ContactDetailsView view;

    public ContactDetailsPresenterImpl(ContactDetailsView view){
        this.view = view;
    }

    @Override
    public Contact getContactFromDb(long id) {
        return ContactDAO.getContact(id);
    }
}
