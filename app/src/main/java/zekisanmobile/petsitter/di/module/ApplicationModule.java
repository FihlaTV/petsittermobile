package zekisanmobile.petsitter.di.module;

import android.app.Application;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.di.DependencyInjector;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.controller.contact.ContactController;
import zekisanmobile.petsitter.job.BaseJob;
import zekisanmobile.petsitter.model.AnimalModel;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.model.UserModel;
import zekisanmobile.petsitter.util.L;

@Module
public class ApplicationModule {

    //region Members
    PetSitterApp application;
    //endregion

    //region Constructors
    public ApplicationModule(PetSitterApp application) {
        this.application = application;
    }
    //endregion

    //region Providers/Singletons
    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    UserModel providesUserModel() {
        return new UserModel(this.application.getAppComponent());
    }

    @Provides
    @Singleton
    AnimalModel providesAnimalModel() {
        return new AnimalModel(this.application.getAppComponent());
    }

    @Provides
    @Singleton
    public OwnerModel providesOwnerModel() {
        return new OwnerModel(this.application.getAppComponent());
    }

    @Provides
    @Singleton
    SitterModel providesSitterModel() {
        return new SitterModel(this.application.getAppComponent());
    }

    @Provides
    @Singleton
    ContactModel providesContactModel() {
        return new ContactModel(this.application.getAppComponent());
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    JobManager providesJobManager() {
        Configuration config = new Configuration.Builder(application)
                .consumerKeepAlive(45)
                .maxConsumerCount(3)
                .minConsumerCount(1)
                .customLogger(L.getJobLogger())
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if (job instanceof BaseJob){
                            ((BaseJob) job).inject(application.getAppComponent());
                        }
                    }
                })
                .build();
        return new JobManager(config);
    }

    @Provides
    @Singleton
    ContactController providesContactController() {
        return new ContactController(application);
    }

    @Provides
    @Singleton
    Realm providesRealm(){
        return Realm.getDefaultInstance();
    }
    //endregion
}
