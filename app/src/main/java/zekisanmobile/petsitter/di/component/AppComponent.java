package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.Handlers.GetSitterContactsHandler;
import zekisanmobile.petsitter.Handlers.GetSittersHandler;
import zekisanmobile.petsitter.Handlers.SearchHandler;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.Sitter.SitterHomeActivity;
import zekisanmobile.petsitter.controller.ContactController;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;
import zekisanmobile.petsitter.job.contact.FetchSitterContactsJob;
import zekisanmobile.petsitter.job.contact.SendContactStatusJob;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface AppComponent {

    void inject(GetSittersHandler getSittersHandler);

    void inject(SearchHandler searchHandler);

    void inject(GetSitterContactsHandler getSitterContactsHandler);

    void inject(MainActivity mainActivity);

    void inject(zekisanmobile.petsitter.Sitter.ContactDetailsActivity contactDetailsActivity);

    void inject(SitterHomeActivity sitterHomeActivity);

    void inject(SendContactStatusJob sendContactStatusJob);

    void inject(FetchSitterContactsJob fecthSitterContactsJob);

    void inject(ContactController contactController);

}
