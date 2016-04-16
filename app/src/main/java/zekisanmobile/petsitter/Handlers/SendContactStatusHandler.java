package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendContactStatusHandler extends AsyncTask<String, Void, Void>{

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/contacts/";
    private final static String FINAL_SEARCH_URL = "/update_status";
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
