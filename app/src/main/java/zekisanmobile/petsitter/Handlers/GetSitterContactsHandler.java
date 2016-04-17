package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.Sitter.SitterHomePresenter;
import zekisanmobile.petsitter.Sitter.SitterHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.util.MyJSONConverter;
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
            contactModel.saveAll(contacts);
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
