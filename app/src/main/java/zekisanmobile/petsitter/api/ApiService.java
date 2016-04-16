package zekisanmobile.petsitter.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import zekisanmobile.petsitter.model.Sitter;

public interface ApiService {

    @GET("sitters.json")
    Call<List<Sitter>> listSitters();

}
