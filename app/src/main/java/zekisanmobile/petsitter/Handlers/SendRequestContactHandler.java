package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.birbit.android.jobqueue.JobManager;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;

public class SendRequestContactHandler extends AsyncTask<String, Void, Void>{

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASE_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/pet_owners/";
    private final static String FINAL_SEARCH_URL = "/request_contact";
    private OkHttpClient client = new OkHttpClient();
    private long owner_api_id;

    @Inject
    JobManager jobManager;

    public SendRequestContactHandler(PetSitterApp petSitterApp) {
        petSitterApp.getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        this.owner_api_id = Long.parseLong(params[1]);
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

    @Override
    protected void onPostExecute(Void v) {
        jobManager.addJobInBackground(
                new FetchOwnerContactsJob(BaseJob.BACKGROUND, owner_api_id)
        );
    }
}
