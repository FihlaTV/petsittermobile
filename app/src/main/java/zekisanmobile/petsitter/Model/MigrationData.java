package zekisanmobile.petsitter.Model;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
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

        if (oldVersion == 10){
            RealmObjectSchema ownerSchema = schema.create("Owner")
                    .addField("id", Long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("api_identification", Long.class)
                    .addField("nome", String.class);

            RealmObjectSchema sitterSchema = schema.create("Sitter")
                    .addField("id", Long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("api_identification", Long.class)
                    .addField("name", String.class)
                    .addField("address", String.class)
                    .addField("district", String.class)
                    .addField("photo", Integer.class)
                    .addField("profile_background", Integer.class)
                    .addField("latitude", Float.class)
                    .addField("longitude", Float.class)
                    .addField("value_hour", Double.class)
                    .addField("value_shift", Double.class)
                    .addField("value_day", Double.class)
                    .addField("about_me", String.class);

            schema.get("User")
                    .addRealmObjectField("sitter", sitterSchema)
                    .addRealmObjectField("owner", ownerSchema);
        }

        if (oldVersion == 11) {
            RealmObjectSchema sitterSchema = schema.get("Sitter");
            RealmObjectSchema ownerSchema = schema.get("Owner");
            schema.create("Contact")
                    .addField("id", Long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("date_start", Date.class)
                    .addField("date_final", Date.class)
                    .addField("time_start", String.class)
                    .addField("time_final", String.class)
                    .addRealmObjectField("sitter", sitterSchema)
                    .addRealmObjectField("owner", ownerSchema);
        }

    }
}
