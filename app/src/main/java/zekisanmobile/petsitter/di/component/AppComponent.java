package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.Handlers.GetSittersHandler;
import zekisanmobile.petsitter.Handlers.SearchHandler;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface AppComponent {

    void inject(GetSittersHandler getSittersHandler);

    void inject(SearchHandler searchHandler);

    void inject(MainActivity mainActivity);

}
