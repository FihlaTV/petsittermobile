package zekisanmobile.petsitter.Owner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;

import zekisanmobile.petsitter.Handlers.SendContactStatusHandler;
import zekisanmobile.petsitter.model.Animal;
import zekisanmobile.petsitter.model.Contact;
import zekisanmobile.petsitter.Sitter.ContactDetailsView;
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
    public String getContactSitterName() {
        return this.contact.sitter.name;
    }

    @Override
    public String getContactSitterPhoto() {
        return this.contact.sitter.photo;
    }

    @Override
    public String getContactDistrict() {
        return this.contact.sitter.district;
    }

    @Override
    public String getContactAddress() {
        return this.contact.sitter.address;
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
    public double getContactSitterLatitude() {
        return this.contact.sitter.latitude;
    }

    @Override
    public double getContactSitterLongitude() {
        return this.contact.sitter.longitude;
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
