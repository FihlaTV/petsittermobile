package zekisanmobile.petsitter;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import zekisanmobile.petsitter.config.PetSitterConfig;
import zekisanmobile.petsitter.di.component.AppComponent;
import zekisanmobile.petsitter.di.component.DaggerAppComponent;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;

public class PetSitterApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        //Realm.deleteRealm(config);

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(PetSitterConfig.getBaseUrl()))
                .build();

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
