package zekisanmobile.petsitter.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import zekisanmobile.petsitter.model.Sitter;
import zekisanmobile.petsitter.vo.Contact;

public interface ApiService {

    @GET("sitters.json")
    Call<List<Sitter>> listSitters();

    @GET("sitters/{sitter}/contacts.json")
    Call<List<Contact>> sitterContacts(@Path("sitter") String sitter_id);

    @POST("pet_owners/{user}/search_sitters")
    Call<List<Sitter>> searchSitters(@Path("user") String user_id, @Body String body);

    @POST("contacts/{contact}/update_status")
    Call sendContactStatusUpdate(@Path("contact") String contact_id, @Body String body);



}
