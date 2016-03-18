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

import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Owner.OwnerHomeView;

public class JSONResponseHandler extends AsyncTask<String, Void, ArrayList<Sitter>> {

    private final String TAG = JSONResponseHandler.class.getSimpleName();
    private ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();
    private SitterFragment sitterFragment;
    private OwnerHomeView view;

    public JSONResponseHandler(SitterFragment sitterFragment, OwnerHomeView view){
        this.view = view;
        this.sitterFragment = sitterFragment;
    }

    @Override
    protected void onPreExecute(){
        if (sitterFragment.isAdded()) sitterFragment.showProgress(true);
    }

    @Override
    protected ArrayList<Sitter> doInBackground(String... url) {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        Request request = new Request.Builder()
                .url(url[0])
                .build();
        try {
            Response response  = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                returnedSitters.add(new Sitter(jsonObject.getLong("id"),
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
                        jsonObject.getString("about_me")));

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
        if (sitterFragment.isAdded()) sitterFragment.showProgress(false);
        view.updateSitterList(returnedSitters);
    }

    @Override
    protected void onCancelled(){
        sitterFragment.showProgress(false);
    }
}
