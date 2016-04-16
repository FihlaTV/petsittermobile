package zekisanmobile.petsitter.Sitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;

import zekisanmobile.petsitter.Handlers.SendContactStatusHandler;
import zekisanmobile.petsitter.model.Animal;
import zekisanmobile.petsitter.model.Contact;
import zekisanmobile.petsitter.util.Formatter;

public class ContactDetailsPresenterImpl implements ContactDetailsPresenter {

    private ContactDetailsView view;
    private Contact contact;

    public ContactDetailsPresenterImpl(ContactDetailsView view){
        this.view = view;
    }

    @Override
    public void getContactFromDb(long id) {
        this.contact = Contact.load(Contact.class, id);
    }

    @Override
    public String getContactOwnerName() {
        return this.contact.owner.name;
    }

    @Override
    public String getContactPhoto() {
        return null;
    }

    @Override
    public String getContactDistrict() {
        return this.contact.owner.district;
    }

    @Override
    public String getContactAddress() {
        return this.contact.owner.address;
    }

    @Override
    public String getContactStartDate() {
        return Formatter.formattedDateFromString(this.contact.dateStart);
    }

    @Override
    public String getContactDatePeriod() {
        return Formatter.formattedDateFromString(this.contact.dateStart)
                + " - "
                + Formatter.formattedDateFromString(this.contact.dateFinal);
    }

    @Override
    public String getContactTimePeriod() {
        return this.contact.timeStart + " - " + this.contact.timeFinal;
    }

    @Override
    public String getContactTotalValue() {
        return NumberFormat.getCurrencyInstance().format(this.contact.totalValue);
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
        return this.contact.owner.latitude;
    }

    @Override
    public double getContactOwnerLongitude() {
        return this.contact.owner.longitude;
    }

    @Override
    public boolean isAccepted() {
        return this.contact.status == 30;
    }

    @Override
    public boolean isFinished() {
        return this.contact.status == 40;
    }

    @Override
    public boolean isRejected() {
        return this.contact.status == 20;
    }

    @Override
    public boolean isAcceptedOrRejectedOrFinished() {
        return isAccepted() || isRejected() || isFinished();
    }

    @Override
    public void acceptContact() {
        sendStatusUpdate(this.contact.apiId, 30);
        Contact.updateStatus(this.contact.apiId, 30);
    }

    @Override
    public void deleteContact() {
        sendStatusUpdate(this.contact.apiId, 20);
        Contact.delete(this.contact.getId());
    }

    private void sendStatusUpdate(long contact_id, int status) {
        try {
            JSONObject jsonContact = new JSONObject();
            jsonContact.put("id", contact_id);
            jsonContact.put("status", status);
            String[] params = { jsonContact.toString(), String.valueOf(contact_id) };
            new SendContactStatusHandler(view.getPetSitterApp()).execute(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
