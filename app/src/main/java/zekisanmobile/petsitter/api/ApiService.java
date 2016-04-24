package zekisanmobile.petsitter.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.vo.Contact;

public interface ApiService {
    @GET("sitters.json")
    Call<List<Sitter>> listSitters();

    @GET("sitters/{sitter}/contacts.json")
    Call<List<Contact>> sitterContacts(@Path("sitter") String sitter_id);

    @GET("pet_owners/{owner}/contacts.json")
    Call<List<Contact>> ownerContacts(@Path("owner") String owner_id);

    @POST("pet_owners/{user}/search_sitters")
    Call<List<Sitter>> searchSitters(@Path("user") String user_id, @Body SearchSittersBody body);

    @POST("contacts/{contact}/update_status")
    Call<Void> sendContactStatusUpdate(@Path("contact") String contact_id, @Body SendContactStatusBody body);

    @POST("pet_owners/{owner}/request_contact")
    Call<Void> sendContactRequest(@Path("owner") String owner_id, @Body ContactRequestBody body);

    @POST("pet_owners/{owner}/rate_contact")
    Call<Void> rateContact(@Path("owner") String owner_id, @Body RateContactBody body);

    @POST("rates/{rate}/reply_rate")
    Call<Void> replyContact(@Path("rate") String rate_id, @Body ReplyRateBody body);
}
