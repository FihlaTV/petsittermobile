package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.handler.GetSitterContactsHandler;
import zekisanmobile.petsitter.handler.GetSittersHandler;
import zekisanmobile.petsitter.handler.SearchHandler;
import zekisanmobile.petsitter.handler.SendRequestContactHandler;
import zekisanmobile.petsitter.view.main.MainActivity;
import zekisanmobile.petsitter.view.owner.ContactDetailsPresenter;
import zekisanmobile.petsitter.view.owner.MyPetSittersPresenter;
import zekisanmobile.petsitter.view.owner.NewContactActivity;
import zekisanmobile.petsitter.view.owner.OwnerHomeActivity;
import zekisanmobile.petsitter.view.owner.OwnerHomePresenter;
import zekisanmobile.petsitter.view.sitter.OtherContactsPresenter;
import zekisanmobile.petsitter.view.sitter.SitterHomeActivity;
import zekisanmobile.petsitter.view.sitter.SitterHomePresenter;
import zekisanmobile.petsitter.controller.contact.ContactController;
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

    void inject(zekisanmobile.petsitter.view.sitter.ContactDetailsActivity contactDetailsActivity);

    void inject(SitterHomeActivity sitterHomeActivity);

    void inject(OwnerHomeActivity ownerHomeActivity);

    void inject(SendContactStatusJob sendContactStatusJob);

    void inject(FetchSitterContactsJob fecthSitterContactsJob);

    void inject(FetchOwnerContactsJob fetchOwnerContactsJob);

    void inject(ContactController contactController);

    void inject(ContactDetailsPresenter contactDetailsPresenter);

    void inject(zekisanmobile.petsitter.view.sitter.ContactDetailsPresenter contactDetailsPresenter);

    void inject(MyPetSittersPresenter myPetSittersPresenter);

    void inject(OwnerHomePresenter ownerHomePresenter);

    void inject(OtherContactsPresenter otherContactsPresenter);

    void inject(SitterHomePresenter sitterHomePresenter);

}
