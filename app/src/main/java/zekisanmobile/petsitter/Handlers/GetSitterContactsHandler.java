package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import zekisanmobile.petsitter.Sitter.SitterHomePresenter;
import zekisanmobile.petsitter.util.MyJSONConverter;

public class GetSitterContactsHandler extends AsyncTask<String, Void, Void> {

    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters/";
    private final static String FINAL_SEARCH_URL = "/contacts";
    private OkHttpClient client = new OkHttpClient();
    private SitterHomePresenter presenter;

    public GetSitterContactsHandler(SitterHomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Void doInBackground(String... params) {
        Request request = new Request.Builder()
                .url(BASE_SEARCH_URL + params[0] + FINAL_SEARCH_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            MyJSONConverter.convertContacts(new JSONArray(jsonData));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        presenter.updateContacts();
    }
}
