package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.api.ApiService;

public class SendContactStatusHandler extends AsyncTask<String, Void, Void>{

    @Inject
    Retrofit retrofit;

    public SendContactStatusHandler(PetSitterApp app){
        app.getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        ApiService service = retrofit.create(ApiService.class);
        Call call = service.sendContactStatusUpdate(params[1], params[0]);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
