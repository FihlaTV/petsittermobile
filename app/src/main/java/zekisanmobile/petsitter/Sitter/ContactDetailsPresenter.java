package zekisanmobile.petsitter.Sitter;

public interface ContactDetailsPresenter {

    void getContactFromDb(long id);

    String getContactOwnerName();
    String getContactPhoto();
    String getContactDistrict();
    String getContactAddress();
    String getContactDatePeriod();
    String getContactTimePeriod();

    double getContactOwnerLatitude();
    double getContactOwnerLongitude();
}
