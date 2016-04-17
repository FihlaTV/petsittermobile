package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import com.birbit.android.jobqueue.JobManager;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.api.NetworkException;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;

public class SendRequestContactHandler extends AsyncTask<String, Void, Void>{

    private long owner_api_id;

    @Inject
    JobManager jobManager;

    @Inject
    Retrofit retrofit;

    public SendRequestContactHandler(PetSitterApp petSitterApp) {
        petSitterApp.getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        owner_api_id = Long.parseLong(params[1]);
        ApiService service = retrofit.create(ApiService.class);
        try {
            Response response = service.sendContactRequest(params[1], params[0]).execute();
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
