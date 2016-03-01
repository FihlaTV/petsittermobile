package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

public class GetContactsHandler extends AsyncTask<String, Void, Void>{

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters/";
    private final static String FINAL_SEARCH_URL = "/contacts";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected Void doInBackground(String... params) {
        RequestBody body = RequestBody.create(JSON, params[0]);
        Request request = new Request.Builder()
                .url(BASE_SEARCH_URL + params[1] + FINAL_SEARCH_URL)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
