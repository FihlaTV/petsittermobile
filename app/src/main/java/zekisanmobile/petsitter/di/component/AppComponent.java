package zekisanmobile.petsitter.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zekisanmobile.petsitter.Handlers.GetSittersHandler;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;
import zekisanmobile.petsitter.model.UserModel;

@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface AppComponent {

    UserModel userModel();

    void inject(GetSittersHandler getSittersHandler);

    void inject(MainActivity mainActivity);

}
