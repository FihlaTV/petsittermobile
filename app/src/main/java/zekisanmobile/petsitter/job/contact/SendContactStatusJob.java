package zekisanmobile.petsitter.job.contact;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.job.BaseJob;

public class SendContactStatusJob extends BaseJob {

    private static final String GROUP = "SendContactStatusJob";
    private final String contactId;
    private final String requestBody;

    @Inject
    transient Retrofit retrofit;

    public SendContactStatusJob(@Priority int priority, @Nullable String contactId,
                                @Nullable String requestBody) {
        super(new Params(priority).addTags(GROUP).requireNetwork());
        this.contactId = contactId;
        this.requestBody = requestBody;
    }

    @Override
    public void inject(AppComponent appComponent){
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onAdded() {}

    @Override
    public void onRun() throws Throwable {
        ApiService service = retrofit.create(ApiService.class);
        Call call = service.sendContactStatusUpdate(contactId, requestBody);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancel(int cancelReason) {}

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
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
