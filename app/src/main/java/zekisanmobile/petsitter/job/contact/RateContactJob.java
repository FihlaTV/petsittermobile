package zekisanmobile.petsitter.job.contact;

import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.api.RateContactBody;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.job.BaseJob;

public class RateContactJob extends BaseJob {

    private static final String GROUP = "RateContactJob";
    private final Long ownerId;
    private final Long contactId;
    private final boolean positive;
    private final String text;

    @Inject
    transient Retrofit retrofit;

    public RateContactJob(@Priority int priority, @Nullable Long ownerId,
                          @Nullable Long contactId, @Nullable boolean positive,
                          @Nullable String text) {
        super(new Params(priority).addTags(GROUP).requireNetwork());
        this.ownerId = ownerId;
        this.contactId = contactId;
        this.positive = positive;
        this.text = text;
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
            RateContactBody body = new RateContactBody();
            body.setContact_id(contactId);
            body.setPositive(positive);
            body.setText(text);
            ApiService service = retrofit.create(ApiService.class);
            Call call = service.rateContact(String.valueOf(ownerId), body);
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancel(int cancelReason) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
