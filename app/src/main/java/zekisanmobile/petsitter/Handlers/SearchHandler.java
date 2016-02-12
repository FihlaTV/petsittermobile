package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SearchHandler extends AsyncTask<String, Void, String> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {

        RequestBody body = RequestBody.create(JSON, params[1]);
        Request request = new Request.Builder()
                .url(params[0])
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("Search POST Response", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String temp) {

    }
}
