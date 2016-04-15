package zekisanmobile.petsitter.Sitter;

import java.util.List;

import zekisanmobile.petsitter.model.Contact;
import zekisanmobile.petsitter.model.Sitter;

public class OtherContactsPresenterImpl implements OtherContactsPresenter {

    private OtherContactsView view;
    private Sitter sitter;

    public OtherContactsPresenterImpl(OtherContactsView view, long sitter_id){
        this.view = view;
        this.sitter = Sitter.load(Sitter.class, sitter_id);
    }

    @Override
    public List<Contact> getContacts(String type) {
        switch (type){
            case "next":
                return sitter.getNextContacts(sitter.getId());
            case "finished":
                return sitter.getFinishedContacts(sitter.getId());
        }
        return null;
    }

    @Override
    public String getTitle(String contactsType) {
        switch (contactsType){
            case "next":
                return "Próximos Trabalhos";
            case "finished":
                return "Trabalhos Finalizados";
        }
        return null;
    }
}
