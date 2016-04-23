package zekisanmobile.petsitter.controller.contact;

import com.birbit.android.jobqueue.JobManager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.api.SendContactStatusBody;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;
import zekisanmobile.petsitter.job.contact.FetchSitterContactsJob;
import zekisanmobile.petsitter.job.contact.SendContactStatusJob;

public class ContactController {

    @Inject
    JobManager jobManager;

    public ContactController(PetSitterApp app) {
        app.getAppComponent().inject(this);
    }

    public void sendContactUpdateAsync(boolean fromUI, long contactId, SendContactStatusBody body) {
        jobManager.addJobInBackground(new SendContactStatusJob(
                fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, String.valueOf(contactId),
                body));
    }

    public void fetchSitterContactsAsync(boolean fromUI, long sitterId) {
        jobManager.addJobInBackground(
                new FetchSitterContactsJob(fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, sitterId)
        );
    }

    public void fetchOwnerContactsAsync(boolean fromUI, long ownerId) {
        jobManager.addJobInBackground(
                new FetchOwnerContactsJob(fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, ownerId)
        );
    }
}
