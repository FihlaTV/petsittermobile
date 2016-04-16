package zekisanmobile.petsitter.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    //region Members
    Application application;
    //endregion

    //region Constructors
    public ApplicationModule(Application application) {
        this.application = application;
    }
    //endregion

    @Provides
    @Singleton
    Application providesApplication(){
        return application;
    }
}
