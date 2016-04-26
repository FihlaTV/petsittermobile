package zekisanmobile.petsitter.view.sitter;

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
    String getContactOwnerComment();

    String[] getContactAnimals();

    double getContactOwnerLatitude();
    double getContactOwnerLongitude();

    boolean isAccepted();
    boolean isRejected();
    boolean isFinished();
    boolean isFinishedAndNotRated();
    boolean isFinishedAndRated();
    boolean isAcceptedOrRejectedOrFinished();
    boolean isContactRatePositive();

    void acceptContact();
    void deleteContact();
}
