package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.view.sitter.SitterHomePresenter;
import zekisanmobile.petsitter.view.sitter.SitterHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;

public class GetSitterContactsHandler extends AsyncTask<String, Void, Void> {

    private SitterHomePresenter presenter;
    private List<Contact> contacts;

    @Inject
    Retrofit retrofit;

    @Inject
    ContactModel contactModel;

    public GetSitterContactsHandler(SitterHomePresenter presenter, SitterHomeView view) {
        view.getPetSitterApp().getAppComponent().inject(this);
        this.presenter = presenter;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            ApiService service = retrofit.create(ApiService.class);
            Call<List<Contact>> call = service.sitterContacts(params[0]);
            contacts = call.execute().body();
            for (Contact contact : contacts) {
                contactModel.insertOrUpdateContact(contact.getId(), contact.getDateStart(),
                        contact.getDateFinal(), contact.getTimeStart(), contact.getTimeFinal(),
                        contact.getCreatedAt(), contact.getSitter(), contact.getOwner(),
                        contact.getTotalValue(), contact.getStatus(), contact.getAnimals());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        presenter.updateContacts();
    }
}
