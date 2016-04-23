package zekisanmobile.petsitter.view.sitter;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

public class OtherContactsPresenterImpl implements OtherContactsPresenter {

    private OtherContactsView view;
    private Sitter sitter;

    @Inject
    SitterModel sitterModel;

    public OtherContactsPresenterImpl(OtherContactsView view, long sitter_id){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.sitter = sitterModel.find(Realm.getDefaultInstance(), sitter_id);
    }

    @Override
    public List<Contact> getContacts(String type) {
        switch (type){
            case "next":
                return sitterModel.getNextContacts(Realm.getDefaultInstance(), sitter.getId());
            case "finished":
                return sitterModel.getFinishedContacts(Realm.getDefaultInstance(), sitter.getId());
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
