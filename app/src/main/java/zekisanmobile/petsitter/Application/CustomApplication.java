package zekisanmobile.petsitter.Application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import zekisanmobile.petsitter.Model.MigrationData;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("realm-petsitter.realm")
                .schemaVersion(11)
                .migration(new MigrationData())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);


    }

}
