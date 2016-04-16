package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.Handlers.GetSittersHandler;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface NetComponent {

    void inject(GetSittersHandler getSittersHandler);

}
