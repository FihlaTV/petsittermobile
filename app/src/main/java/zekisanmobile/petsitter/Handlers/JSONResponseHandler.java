package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.model.Sitter;
import zekisanmobile.petsitter.Owner.OwnerHomeView;
import zekisanmobile.petsitter.util.HandlersUtil;
import zekisanmobile.petsitter.util.MyJSONConverter;

public class JSONResponseHandler extends AsyncTask<Void, Void, ArrayList<Sitter>> {

    private final String TAG = JSONResponseHandler.class.getSimpleName();
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
                .url(HandlersUtil.getAllSittersURL())
                .build();
        try {
            Response response  = client.newCall(request).execute();
            String jsonData = response.body().string();
            return MyJSONConverter.convertSitters(new JSONArray(jsonData));
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
        }
        if (receivedSitters != null && receivedSitters.size() > 0) view.updateSitterList(receivedSitters);
    }

    @Override
    protected void onCancelled(){
        sitterFragment.showProgress(false);
    }
}
