package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;

public class GetOwnerContactsHandler extends AsyncTask<String, Void, Void> {

    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/pet_owners/";
    private final static String FINAL_SEARCH_URL = "/contacts";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected Void doInBackground(String... params) {
        /*Request request = new Request.Builder()
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
        }*/
        return null;
    }
}
