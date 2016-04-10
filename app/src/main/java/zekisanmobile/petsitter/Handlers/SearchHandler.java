package zekisanmobile.petsitter.Handlers;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Owner.OwnerHomeActivity;
import zekisanmobile.petsitter.Util.MyJSONConverter;

public class SearchHandler extends AsyncTask<String, Void, ArrayList<Sitter>> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/pet_owners/";
    private final static String FINAL_SEARCH_URL = "/search_sitters";
    private OkHttpClient client = new OkHttpClient();
    private Context context;

    public SearchHandler(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(String... params) {

        try {
            RequestBody body = RequestBody.create(JSON, params[0]);
            Request request = new Request.Builder()
                    .url(BASE_SEARCH_URL + params[1] + FINAL_SEARCH_URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();

            return MyJSONConverter.convertSitters(new JSONArray(jsonData));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Sitter> sitters) {
        ((OwnerHomeActivity) context).updateSitterList(sitters);
    }
}
