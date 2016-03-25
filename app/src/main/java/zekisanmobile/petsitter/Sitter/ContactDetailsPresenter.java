package zekisanmobile.petsitter.Sitter;

public interface ContactDetailsPresenter {

    void getContactFromDb(long id);

    String getContactOwnerName();
    String getContactPhoto();
    String getContactDistrict();
    String getContactAddress();
    String getContactStartDate();
    String getContactDatePeriod();
    String getContactTimePeriod();
    String getContactTotalValue();

    String[] getContactAnimals();

    double getContactOwnerLatitude();
    double getContactOwnerLongitude();

    void acceptContact();
    void deleteContact();
}
