package zekisanmobile.petsitter.job.contact;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import zekisanmobile.petsitter.api.ApiService;
import zekisanmobile.petsitter.api.body.ReplyRateBody;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.job.BaseJob;

public class ReplyRateJob extends BaseJob {

    private static final String GROUP = "ReplyRateJob";

    final long rateId;
    final String text;

    @Inject
    transient Retrofit retrofit;

    public ReplyRateJob(@Priority int priority, long rateId, String text) {
        super(new Params(priority).addTags(GROUP).requireNetwork());
        this.rateId = rateId;
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
        ReplyRateBody body = new ReplyRateBody();
        body.setText(text);
        ApiService service = retrofit.create(ApiService.class);
        Call call = service.replyContact(String.valueOf(rateId), body);
    }

    @Override
    protected void onCancel(int cancelReason) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
