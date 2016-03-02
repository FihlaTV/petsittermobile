package zekisanmobile.petsitter.Handlers;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import zekisanmobile.petsitter.Model.Contact;

public class GetContactsHandler extends AsyncTask<String, Void, ArrayList<Contact>>{

    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters/";
    private final static String FINAL_SEARCH_URL = "/contacts";
    private OkHttpClient client = new OkHttpClient();

    private ArrayList<Contact> returnedContacts = new ArrayList<Contact>();

    private Context context;

    public GetContactsHandler(Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        Request request = new Request.Builder()
                .url(BASE_SEARCH_URL + params[1] + FINAL_SEARCH_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // TODO: localizar ou criar o sitter do contact
                // TODO: localizar ou criar o owner do contact
                // TODO: localizar ou criar o contact
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> receivedContacts) {
        // TODO: passar os contacts para a Activity
    }
}
