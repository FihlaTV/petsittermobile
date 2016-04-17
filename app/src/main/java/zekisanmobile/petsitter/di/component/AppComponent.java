package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.Handlers.GetSitterContactsHandler;
import zekisanmobile.petsitter.Handlers.GetSittersHandler;
import zekisanmobile.petsitter.Handlers.SearchHandler;
import zekisanmobile.petsitter.Handlers.SendRequestContactHandler;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.Owner.ContactDetailsPresenter;
import zekisanmobile.petsitter.Owner.MyPetSittersPresenter;
import zekisanmobile.petsitter.Owner.NewContactActivity;
import zekisanmobile.petsitter.Owner.OwnerHomeActivity;
import zekisanmobile.petsitter.Owner.OwnerHomePresenter;
import zekisanmobile.petsitter.Sitter.OtherContactsPresenter;
import zekisanmobile.petsitter.Sitter.SitterHomeActivity;
import zekisanmobile.petsitter.Sitter.SitterHomePresenter;
import zekisanmobile.petsitter.controller.ContactController;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;
import zekisanmobile.petsitter.job.contact.FetchOwnerContactsJob;
import zekisanmobile.petsitter.job.contact.FetchSitterContactsJob;
import zekisanmobile.petsitter.job.contact.SendContactStatusJob;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface AppComponent {

    void inject(GetSittersHandler getSittersHandler);

    void inject(SearchHandler searchHandler);

    void inject(GetSitterContactsHandler getSitterContactsHandler);

    void inject(SendRequestContactHandler sendRequestContactHandler);

    void inject(MainActivity mainActivity);

    void inject(NewContactActivity newContactActivity);

    void inject(zekisanmobile.petsitter.Sitter.ContactDetailsActivity contactDetailsActivity);

    void inject(SitterHomeActivity sitterHomeActivity);

    void inject(OwnerHomeActivity ownerHomeActivity);

    void inject(SendContactStatusJob sendContactStatusJob);

    void inject(FetchSitterContactsJob fecthSitterContactsJob);

    void inject(FetchOwnerContactsJob fetchOwnerContactsJob);

    void inject(ContactController contactController);

    void inject(ContactDetailsPresenter contactDetailsPresenter);

    void inject(zekisanmobile.petsitter.Sitter.ContactDetailsPresenter contactDetailsPresenter);

    void inject(MyPetSittersPresenter myPetSittersPresenter);

    void inject(OwnerHomePresenter ownerHomePresenter);

    void inject(OtherContactsPresenter otherContactsPresenter);

    void inject(SitterHomePresenter sitterHomePresenter);

}
