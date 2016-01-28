package zekisanmobile.petsitter.Model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MigrationData implements RealmMigration {

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 8) {
            RealmObjectSchema userSchema = schema.get("User");

            userSchema.addField("photo", String.class);
        }

    }
}
