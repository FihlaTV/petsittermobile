package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import com.birbit.android.jobqueue.JobManager;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.api.ContactRequestBody;
import zekisanmobile.petsitter.api.NetworkException;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;

public class SendRequestContactHandler extends AsyncTask<Void, Void, Void>{

    private long owner_api_id;

    @Inject
    JobManager jobManager;

    @Inject
    Retrofit retrofit;

    ContactRequestBody body;

    public SendRequestContactHandler(PetSitterApp petSitterApp, long owner_api_id,
                                     ContactRequestBody body) {
        petSitterApp.getAppComponent().inject(this);
        this.owner_api_id = owner_api_id;
        this.body = body;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ApiService service = retrofit.create(ApiService.class);
        try {
            Response response = service.sendContactRequest(String.valueOf(owner_api_id), body).execute();
            if (response.isSuccessful()){
                jobManager.addJobInBackground(
                        new FetchOwnerContactsJob(BaseJob.BACKGROUND, owner_api_id)
                );
            } else {
                throw new NetworkException(response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
