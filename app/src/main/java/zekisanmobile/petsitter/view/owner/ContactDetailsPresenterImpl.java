package zekisanmobile.petsitter.view.owner;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import zekisanmobile.petsitter.view.sitter.ContactDetailsView;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.util.Formatter;

public class ContactDetailsPresenterImpl implements ContactDetailsPresenter {

    private ContactDetailsView view;
    private Contact contact;
    @Inject
    ContactModel contactModel;

    public ContactDetailsPresenterImpl(ContactDetailsView view){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
    }

    @Override
    public void getContactFromDb(long id) {
        this.contact = contactModel.find(Realm.getDefaultInstance(), id);
    }

    @Override
    public String getContactSitterName() {
        return this.contact.getSitter().getName();
    }

    @Override
    public String getContactSitterPhoto() {
        return this.contact.getSitter().getPhoto();
    }

    @Override
    public String getContactDistrict() {
        return this.contact.getSitter().getDistrict();
    }

    @Override
    public String getContactAddress() {
        return this.contact.getSitter().getAddress();
    }

    @Override
    public String getContactStartDate() {
        return Formatter.formatDateToString(this.contact.getDateStart());
    }

    @Override
    public String getContactDatePeriod() {
        return Formatter.formatDateToString(this.contact.getDateStart())
                + " - "
                + Formatter.formatDateToString(this.contact.getDateFinal());
    }

    @Override
    public String getContactTimePeriod() {
        return this.contact.getTimeStart() + " - " + this.contact.getTimeFinal();
    }

    @Override
    public String getContactTotalValue() {
        return NumberFormat.getCurrencyInstance().format(this.contact.getTotalValue());
    }

    @Override
    public String[] getContactAnimals() {
        List<Animal> animals = this.contact.getAnimals();
        String[] animalsNames = new String[animals.size()];
        for(int i = 0; i < animals.size(); i++){
            animalsNames[i] = animals.get(i).getName();
        }
        return animalsNames;
    }

    @Override
    public double getContactSitterLatitude() {
        return this.contact.getSitter().getLatitude();
    }

    @Override
    public double getContactSitterLongitude() {
        return this.contact.getSitter().getLongitude();
    }

    @Override
    public long getContactId() {
        return this.contact.getId();
    }

    @Override
    public boolean isAccepted() {
        return this.contact.getStatus() == 30;
    }

    @Override
    public boolean isFinished() {
        return this.contact.getStatus() == 40;
    }

    @Override
    public boolean isRejected() {
        return this.contact.getStatus() == 20;
    }

    @Override
    public boolean isAcceptedOrRejectedOrFinished() {
        return isAccepted() || isRejected() || isFinished();
    }
}
