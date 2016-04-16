package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Owner.OwnerHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.config.PetSitterConfig;
import zekisanmobile.petsitter.model.Sitter;

public class JSONResponseHandler extends AsyncTask<Void, Void, ArrayList<Sitter>> {

    private SitterFragment sitterFragment;
    private OwnerHomeView view;
    List<Sitter> sitters;

    public JSONResponseHandler(SitterFragment sitterFragment, OwnerHomeView view){
        this.view = view;
        this.sitterFragment = sitterFragment;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(Void... params) {
        ObjectMapper mapper = new ObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PetSitterConfig.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<Sitter>> call = service.listSitters();
        try {
            sitters = call.execute().body();
            sitters.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sitters != null){
            return new ArrayList<Sitter>(sitters);
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
