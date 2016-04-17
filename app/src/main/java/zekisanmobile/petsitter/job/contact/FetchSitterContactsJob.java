package zekisanmobile.petsitter.job.contact;

import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.api.NetworkException;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.event.contact.FetchedSitterContactsEvent;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;

public class FetchSitterContactsJob extends BaseJob {

    private static final String GROUP = "FetchSitterContactsJob";
    private final Long sitterId;

    @Inject
    transient EventBus eventBus;

    @Inject
    transient Retrofit retrofit;

    @Inject
    transient ContactModel contactModel;

    public FetchSitterContactsJob(@Priority int priority, @Nullable Long sitterId) {
        super(new Params(priority).addTags(GROUP).requireNetwork());
        this.sitterId = sitterId;
    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        try {
            ApiService service = retrofit.create(ApiService.class);
            Call<List<Contact>> call = service.sitterContacts(String.valueOf(sitterId));
            Response<List<Contact>> response = call.execute();
            if(response.isSuccessful()) {
                handleResponse(response.body());
                eventBus.post(new FetchedSitterContactsEvent(true, sitterId));
            } else {
                throw new NetworkException(response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(List<Contact> contacts){
        contactModel.saveAll(contacts);
    }

    @Override
    protected void onCancel(int cancelReason) {
        eventBus.post(new FetchedSitterContactsEvent(false, sitterId));
    }

    @Override
    public RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                  int maxRunCount) {
        if (shouldRetry(throwable)) {
            return RetryConstraint.createExponentialBackoff(runCount, 1000);
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected int getRetryLimit() {
        return 2;
    }

}
