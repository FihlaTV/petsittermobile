package zekisanmobile.petsitter;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

import zekisanmobile.petsitter.config.PetSitterConfig;
import zekisanmobile.petsitter.di.component.DaggerNetComponent;
import zekisanmobile.petsitter.di.component.NetComponent;
import zekisanmobile.petsitter.di.module.ApplicationModule;
import zekisanmobile.petsitter.di.module.NetModule;

public class PetSitterApp extends com.activeandroid.app.Application {

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);

        netComponent = DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(PetSitterConfig.getBaseUrl()))
                .build();

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

    public NetComponent getNetComponent(){
        return netComponent;
    }
}
