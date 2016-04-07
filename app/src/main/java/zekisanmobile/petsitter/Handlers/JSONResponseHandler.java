package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Owner.OwnerHomeView;

public class JSONResponseHandler extends AsyncTask<Void, Void, ArrayList<Sitter>> {

    private final String TAG = JSONResponseHandler.class.getSimpleName();
    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";
    private SitterFragment sitterFragment;
    private OwnerHomeView view;

    public JSONResponseHandler(SitterFragment sitterFragment, OwnerHomeView view){
        this.view = view;
        this.sitterFragment = sitterFragment;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setRetryOnConnectionFailure(true);
        client.networkInterceptors().add(new StethoInterceptor());
        Request request = new Request.Builder()
                .url(API_SEARCH_URL)
                .build();
        try {
            ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();
            Response response  = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray animalsArray = jsonObject.getJSONArray("animals");
                List<Animal> animals = new ArrayList<>();
                for(int j = 0; j < animalsArray.length(); j++){
                    JSONObject animalObject = animalsArray.getJSONObject(j);
                    Animal animal = new Animal();
                    animal.setId(animalObject.getLong("id"));
                    animal.setName(animalObject.getString("name"));
                    animals.add(animal);
                }

                Sitter sitter = new Sitter(jsonObject.getLong("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("address"),
                        jsonObject.getString("photo"),
                        jsonObject.getString("header_background"),
                        Float.parseFloat(jsonObject.getString("latitude")),
                        Float.parseFloat(jsonObject.getString("longitude")),
                        jsonObject.getString("district"),
                        Double.valueOf(jsonObject.getString("value_hour")),
                        Double.valueOf(jsonObject.getString("value_shift")),
                        Double.valueOf(jsonObject.getString("value_day")),
                        jsonObject.getString("about_me"),
                        animals);
                returnedSitters.add(sitter);

            }
            return returnedSitters;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Sitter> receivedSitters) {
        if (sitterFragment.isAdded()) {
            sitterFragment.showProgress(false);
            sitterFragment.setRunningRequest(false);
        }
        if (receivedSitters != null && receivedSitters.size() > 0) view.updateSitterList(receivedSitters);
    }

    @Override
    protected void onCancelled(){
        sitterFragment.showProgress(false);
    }
}
