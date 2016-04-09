package zekisanmobile.petsitter.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import zekisanmobile.petsitter.Model.MigrationData;

public class CustomApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("realm-petsitter.realm")
                .schemaVersion(13)
                .migration(new MigrationData())
                //.deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        //Realm.deleteRealm(realmConfiguration);

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

}
