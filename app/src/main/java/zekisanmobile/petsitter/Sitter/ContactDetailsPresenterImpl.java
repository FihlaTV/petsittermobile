package zekisanmobile.petsitter.Sitter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Util.Formatter;

public class ContactDetailsPresenterImpl implements ContactDetailsPresenter {

    private ContactDetailsView view;
    private Contact contact;

    public ContactDetailsPresenterImpl(ContactDetailsView view){
        this.view = view;
    }

    @Override
    public void getContactFromDb(long id) {
        this.contact = ContactDAO.getContact(id);
    }

    @Override
    public String getContactOwnerName() {
        return this.contact.getOwner().getNome();
    }

    @Override
    public String getContactPhoto() {
        return null;
    }

    @Override
    public String getContactDistrict() {
        return this.contact.getOwner().getDistrict();
    }

    @Override
    public String getContactAddress() {
        return this.contact.getOwner().getAddress();
    }

    @Override
    public String getContactDatePeriod() {
        return Formatter.formattedDate(this.contact.getDate_start())
                + " - "
                + Formatter.formattedDate(this.contact.getDate_final());
    }

    @Override
    public String getContactTimePeriod() {
        return this.contact.getTime_start() + " - " + this.contact.getTime_final();
    }

    @Override
    public String getContactTotalValue() {
        return NumberFormat.getCurrencyInstance().format(this.contact.getTotalValue());
    }

    @Override
    public String[] getContactAnimals() {
        RealmList<Animal> animals = this.contact.getAnimals();
        String[] animalsNames = new String[animals.size()];
        for(int i = 0; i < animals.size(); i++){
            animalsNames[i] = animals.get(i).getName();
        }
        return animalsNames;
    }

    @Override
    public double getContactOwnerLatitude() {
        return this.contact.getOwner().getLatitude();
    }

    @Override
    public double getContactOwnerLongitude() {
        return this.contact.getOwner().getLongitude();
    }
}
