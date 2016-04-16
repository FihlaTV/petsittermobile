package zekisanmobile.petsitter;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

public class PetSitterApp extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

}
