package zekisanmobile.petsitter;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

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

        FlowManager.init(this);

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
