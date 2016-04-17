package zekisanmobile.petsitter.Sitter;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Animal;
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
        this.contact = contactModel.find(id);
    }

    @Override
    public long getContactApiId() {
        return this.contact.getApiId();
    }

    @Override
    public String getContactOwnerName() {
        return this.contact.getOwner().getName();
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
    public String getContactStartDate() {
        return Formatter.formattedDateFromString(this.contact.getDateStart());
    }

    @Override
    public String getContactDatePeriod() {
        return Formatter.formattedDateFromString(this.contact.getDateStart())
                + " - "
                + Formatter.formattedDateFromString(this.contact.getDateFinal());
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
    public double getContactOwnerLatitude() {
        return this.contact.getOwner().getLatitude();
    }

    @Override
    public double getContactOwnerLongitude() {
        return this.contact.getOwner().getLongitude();
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

    @Override
    public void acceptContact() {
        contactModel.updateStatus(this.contact.getApiId(), 30);
    }

    @Override
    public void deleteContact() {
        contactModel.delete(this.contact.getId());
    }
}
