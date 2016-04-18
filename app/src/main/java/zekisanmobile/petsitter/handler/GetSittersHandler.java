package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.fragment.SitterFragment;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.view.owner.OwnerHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.vo.Sitter;

public class GetSittersHandler extends AsyncTask<Void, Void, ArrayList<Sitter>> {

    private SitterFragment sitterFragment;
    private OwnerHomeView view;
    List<Sitter> sitters;
    @Inject Retrofit retrofit;
    @Inject
    SitterModel sitterModel;

    public GetSittersHandler(SitterFragment sitterFragment, OwnerHomeView view){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.sitterFragment = sitterFragment;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(Void... params) {
        ApiService service = retrofit.create(ApiService.class);
        Call<List<Sitter>> call = service.listSitters();
        List<Sitter> sittersFromDB = new ArrayList<>();
        try {
            sitters = call.execute().body();
            sitterModel.saveAll(sitters);

            for (int i = 0; i < sitters.size(); i++) {
                sittersFromDB.add(sitterModel.findByApiId(sitters.get(i).getApiId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sittersFromDB != null){
            return new ArrayList<Sitter>(sittersFromDB);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Sitter> receivedSitters) {
        if (sitterFragment.isAdded()) {
            sitterFragment.showProgress(false);
        }
        if (receivedSitters != null && receivedSitters.size() > 0) {
            view.updateSitterList(receivedSitters);
        }
    }

    @Override
    protected void onCancelled(){
        sitterFragment.showProgress(false);
    }
}
