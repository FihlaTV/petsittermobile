package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.DAO.OwnerDAO;
import zekisanmobile.petsitter.DAO.SitterDAO;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Owner;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Sitter.SitterHomePresenter;

public class GetContactsHandler extends AsyncTask<String, Void, ArrayList<Contact>>{

    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters/";
    private final static String FINAL_SEARCH_URL = "/contacts";
    private OkHttpClient client = new OkHttpClient();
    private SitterHomePresenter presenter;

    public GetContactsHandler(SitterHomePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        ArrayList<Contact> returnedContacts = new ArrayList<Contact>();
        Request request = new Request.Builder()
                .url(BASE_SEARCH_URL + params[0] + FINAL_SEARCH_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject sitterObject = jsonObject.getJSONObject("sitter");

                Sitter sitter = SitterDAO.insertOrUpdateSitter(
                        sitterObject.getLong("id"), sitterObject.getString("name"), sitterObject.getString("address"),
                        sitterObject.getString("photo"), sitterObject.getString("header_background"),
                        Float.parseFloat(sitterObject.getString("latitude")),
                        Float.parseFloat(sitterObject.getString("longitude")),
                        sitterObject.getString("district"), Double.valueOf(sitterObject.getString("value_hour")),
                        Double.valueOf(sitterObject.getString("value_shift")),
                        Double.valueOf(sitterObject.getString("value_day")), sitterObject.getString("about_me")
                );

                JSONObject ownerObject = jsonObject.getJSONObject("pet_owner");
                Owner owner = OwnerDAO.insertOrUpdateOwner(ownerObject.getLong("id"), ownerObject.getString("name"));

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date_start = formatter.parse(jsonObject.getString("date_start"));
                    Date date_final = formatter.parse(jsonObject.getString("date_final"));

                    Contact contact = ContactDAO.insertOrUpdateContact(jsonObject.getLong("id"),
                            date_start, date_final, jsonObject.getString("time_start"),
                            jsonObject.getString("time_final"),
                            jsonObject.getString("created_at").substring(0, 10),
                            sitter, owner);

                    returnedContacts.add(contact);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return returnedContacts;
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> receivedContacts) {
        if (receivedContacts != null) {
            presenter.updateContacts();
        }
    }
}
