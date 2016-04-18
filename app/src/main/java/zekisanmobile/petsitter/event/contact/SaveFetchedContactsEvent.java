package zekisanmobile.petsitter.event.contact;

import android.support.annotation.Nullable;

import java.util.List;

import zekisanmobile.petsitter.vo.Contact;

public class SaveFetchedContactsEvent {

    private final boolean success;

    private final List<Contact> contacts;

    public SaveFetchedContactsEvent(boolean success, @Nullable List<Contact> contacts) {
        this.success = success;
        this.contacts = contacts;
    }

    public boolean isSuccess() {
        return success;
    }

    @javax.annotation.Nullable
    public List<Contact> getContacts() {
        return contacts;
    }
}
