package zekisanmobile.petsitter.view.owner;

public interface ContactDetailsPresenter {

    void getContactFromDb(long id);

    String getContactSitterName();
    String getContactSitterPhoto();
    String getContactDistrict();
    String getContactAddress();
    String getContactStartDate();
    String getContactDatePeriod();
    String getContactTimePeriod();
    String getContactTotalValue();

    String[] getContactAnimals();

    double getContactSitterLatitude();
    double getContactSitterLongitude();

    boolean isAccepted();
    boolean isRejected();
    boolean isFinished();
    boolean isAcceptedOrRejectedOrFinished();
}
