package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.controller.contact.ContactController;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;
import zekisanmobile.petsitter.handler.GetSitterContactsHandler;
import zekisanmobile.petsitter.handler.GetSittersHandler;
import zekisanmobile.petsitter.handler.SearchHandler;
import zekisanmobile.petsitter.handler.SendRequestContactHandler;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;
import zekisanmobile.petsitter.job.contact.FetchSitterContactsJob;
import zekisanmobile.petsitter.job.contact.RateContactJob;
import zekisanmobile.petsitter.job.contact.ReplyRateJob;
import zekisanmobile.petsitter.job.contact.SendContactStatusJob;
import zekisanmobile.petsitter.model.AnimalModel;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.model.UserModel;
import zekisanmobile.petsitter.view.main.MainActivity;
import zekisanmobile.petsitter.view.owner.ContactDetailsPresenterImpl;
import zekisanmobile.petsitter.view.owner.MyPetSittersPresenterImpl;
import zekisanmobile.petsitter.view.owner.NewContactActivity;
import zekisanmobile.petsitter.view.owner.OwnerHomeActivity;
import zekisanmobile.petsitter.view.owner.OwnerHomePresenterImpl;
import zekisanmobile.petsitter.view.owner.RateActivity;
import zekisanmobile.petsitter.view.owner.RatePresenterImpl;
import zekisanmobile.petsitter.view.owner.SitterProfileActivity;
import zekisanmobile.petsitter.view.sitter.OtherContactsPresenterImpl;
import zekisanmobile.petsitter.view.sitter.SitterHomeActivity;
import zekisanmobile.petsitter.view.sitter.SitterHomePresenterImpl;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface AppComponent {

    void inject(GetSittersHandler getSittersHandler);

    void inject(SearchHandler searchHandler);

    void inject(GetSitterContactsHandler getSitterContactsHandler);

    void inject(SendRequestContactHandler sendRequestContactHandler);

    void inject(MainActivity mainActivity);

    void inject(NewContactActivity newContactActivity);

    void inject(zekisanmobile.petsitter.view.sitter.ContactDetailsActivity contactDetailsActivity);

    void inject(SitterHomeActivity sitterHomeActivity);

    void inject(OwnerHomeActivity ownerHomeActivity);

    void inject(SitterProfileActivity sitterProfileActivity);

    void inject(RateActivity rateActivity);

    void inject(SendContactStatusJob sendContactStatusJob);

    void inject(FetchSitterContactsJob fecthSitterContactsJob);

    void inject(RateContactJob rateContactJob);

    void inject(ReplyRateJob replyRateJob);

    void inject(FetchOwnerContactsJob fetchOwnerContactsJob);

    void inject(ContactController contactController);

    void inject(ContactDetailsPresenterImpl contactDetailsPresenter);

    void inject(zekisanmobile.petsitter.view.sitter.ContactDetailsPresenterImpl contactDetailsPresenter);

    void inject(MyPetSittersPresenterImpl myPetSittersPresenter);

    void inject(OwnerHomePresenterImpl ownerHomePresenter);

    void inject(OtherContactsPresenterImpl otherContactsPresenter);

    void inject(SitterHomePresenterImpl sitterHomePresenter);

    void inject(RatePresenterImpl ratePresenter);

    void inject(AnimalModel animalModel);

    void inject(ContactModel contactModel);

    void inject(OwnerModel ownerModel);

    void inject(SitterModel sitterModel);

    void inject(UserModel userModel);

}
