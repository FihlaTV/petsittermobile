package zekisanmobile.petsitter.Sitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import io.realm.RealmList;
import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.Handlers.SendContactStatusHandler;
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
    public String getContactStartDate() {
        return Formatter.formattedDate(this.contact.getDate_start());
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

    @Override
    public boolean isAccepted() {
        return this.contact.getStatus() == 30;
    }

    @Override
    public boolean isRejected() {
        return this.contact.getStatus() == 20;
    }

    @Override
    public boolean isAcceptedOrRejected() {
        return isAccepted() || isRejected();
    }

    @Override
    public void acceptContact() {
        ContactDAO.updateStatus(this.contact.getId(), 30);
        sendStatusUpdate(30);
    }

    @Override
    public void deleteContact() {
        ContactDAO.deleteContact(this.contact.getId());
        sendStatusUpdate(20);
    }

    private void sendStatusUpdate(int status) {
        try {
            JSONObject jsonContact = new JSONObject();
            jsonContact.put("id", this.contact.getId());
            jsonContact.put("status", status);
            String[] params = { jsonContact.toString(), String.valueOf(this.contact.getId()) };
            new SendContactStatusHandler().execute(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
