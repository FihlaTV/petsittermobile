package zekisanmobile.petsitter.event.contact;

import javax.annotation.Nullable;

public class FetchedSitterContactsEvent {

    private final boolean success;
    @Nullable
    private final Long sitterId;

    public FetchedSitterContactsEvent(boolean success, @Nullable Long sitterId) {
        this.success = success;
        this.sitterId = sitterId;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public Long getSitterId() {
        return sitterId;
    }
}
