package zekisanmobile.petsitter.Sitter;

public interface ContactDetailsPresenter {

    void getContactFromDb(long id);

    long getContactApiId();

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

    boolean isAccepted();
    boolean isRejected();
    boolean isFinished();
    boolean isAcceptedOrRejectedOrFinished();

    void acceptContact();
    void deleteContact();
}
