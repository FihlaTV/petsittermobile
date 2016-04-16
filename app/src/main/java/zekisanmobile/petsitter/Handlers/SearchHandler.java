package zekisanmobile.petsitter.Handlers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.Owner.OwnerHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.model.Sitter;

public class SearchHandler extends AsyncTask<String, Void, ArrayList<Sitter>> {

    private List<Sitter> sitters;
    private OwnerHomeView view;
    @Inject
    Retrofit retrofit;

    public SearchHandler(OwnerHomeView view) {
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(String... params) {
        ApiService service = retrofit.create(ApiService.class);
        Call<List<Sitter>> call = service.searchSitters(params[1], params[0]);
        try {
            sitters = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sitters != null){
            return new ArrayList<Sitter>(sitters);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Sitter> sitters) {
        if (sitters != null && sitters.size() > 0) {
            view.updateSitterList(sitters);
        }
    }
}
