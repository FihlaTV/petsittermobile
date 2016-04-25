package zekisanmobile.petsitter.controller.contact;

import com.birbit.android.jobqueue.JobManager;

import javax.inject.Inject;

import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.api.body.SendContactStatusBody;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;
import zekisanmobile.petsitter.job.contact.FetchSitterContactsJob;
import zekisanmobile.petsitter.job.contact.RateContactJob;
import zekisanmobile.petsitter.job.contact.ReplyRateJob;
import zekisanmobile.petsitter.job.contact.SendContactStatusJob;

public class ContactController {

    //region Members
    @Inject
    JobManager jobManager;
    //endregion

    //region Constructors
    public ContactController(PetSitterApp app) {
        app.getAppComponent().inject(this);
    }
    //endregion

    //region Methods
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

    public void rateContact(boolean fromUI, long ownerId, long contactId, boolean positive,
                            String text) {
        jobManager.addJobInBackground(
                new RateContactJob(fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, ownerId,
                contactId, positive, text)
        );
        fetchOwnerContactsAsync(false, ownerId);
    }

    public void replyRate(boolean fromUI, long rateId, String text) {
        jobManager.addJobInBackground(new ReplyRateJob(
                fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, rateId, text));
    }
    //endregion
}
