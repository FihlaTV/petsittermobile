package zekisanmobile.petsitter.api.body;

public class RateContactBody {

    //region Members
    long contact_id;

    boolean positive;

    String text;
    //endregion

    //region Accessors
    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    //endregion
}
