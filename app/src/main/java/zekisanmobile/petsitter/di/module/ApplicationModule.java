package zekisanmobile.petsitter.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zekisanmobile.petsitter.model.AnimalModel;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.model.UserModel;

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

    //region Providers/Singletons
    @Provides
    @Singleton
    Application providesApplication(){
        return application;
    }

    @Provides
    @Singleton
    UserModel providesUserModel(){
        return new UserModel();
    }

    @Provides
    @Singleton
    AnimalModel providesAnimalModel(){
        return new AnimalModel();
    }

    @Provides
    @Singleton
    OwnerModel providesOwnerModel() {
        return new OwnerModel();
    }

    @Provides
    @Singleton
    SitterModel providesSitterModel() {
        return new SitterModel();
    }
    //endregion
}
