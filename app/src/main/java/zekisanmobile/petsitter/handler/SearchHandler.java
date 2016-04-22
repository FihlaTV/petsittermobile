package zekisanmobile.petsitter.handler;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.api.SearchSittersBody;
import zekisanmobile.petsitter.view.owner.OwnerHomeView;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.vo.Sitter;

public class SearchHandler extends AsyncTask<String, Void, ArrayList<Sitter>> {

    private List<Sitter> sitters;
    private OwnerHomeView view;
    @Inject
    Retrofit retrofit;

    long owner_api_id;
    SearchSittersBody body;

    public SearchHandler(OwnerHomeView view, long owner_api_id, SearchSittersBody body) {
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.owner_api_id = owner_api_id;
        this.body = body;
    }

    @Override
    protected ArrayList<Sitter> doInBackground(String... params) {
        ApiService service = retrofit.create(ApiService.class);
        Call<List<Sitter>> call = service.searchSitters(String.valueOf(owner_api_id), body);
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
