package zekisanmobile.petsitter.Model;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

import zekisanmobile.petsitter.R;

public class MigrationData implements RealmMigration {

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        /*if (oldVersion == 8) {
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
        }*/

        if (oldVersion == 12) {
            createInitialData();
        }
    }

    private void createInitialData() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Owner owner = realm.createObject(Owner.class);
        owner.setId(1);
        owner.setApiId(1);
        owner.setNome("Ezequiel Guilherme");
        realm.commitTransaction();

        realm.beginTransaction();
        Sitter sitter = realm.createObject(Sitter.class);
        sitter.setId(1);
        sitter.setApiId(2);
        sitter.setName("Ezequiel");
        sitter.setAddress("R. Cel. Genuino, 149 - Centro Histórico, Porto Alegre - Rio Grande do Sul");
        sitter.setDistrict("Centro Histórico");
        sitter.setValue_hour(15.0);
        sitter.setValue_shift(60.0);
        sitter.setValue_day(175.0);
        sitter.setAbout_me("Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum sobreviveu não só a cinco séculos, como também ao salto para a editoração eletrônica, permanecendo essencialmente inalterado. Se popularizou na década de 60, quando a Letraset lançou decalques contendo passagens de Lorem Ipsum, e mais recentemente quando passou a ser integrado a softwares de editoração eletrônica como Aldus PageMaker.");
        sitter.setPhoto("sitter2");
        sitter.setProfile_background("header_background_2");
        realm.commitTransaction();

        realm.beginTransaction();
        User userSitter = realm.createObject(User.class);
        userSitter.setId(1);
        userSitter.setName("Ezequiel");
        userSitter.setEmail("zekisan88@gmail.com");
        userSitter.setLogged(true);
        userSitter.setPhoto("sitter2");
        userSitter.setType(1);
        userSitter.setSitter(sitter);
        realm.commitTransaction();

        realm.beginTransaction();
        User userOwner = realm.createObject(User.class);
        userOwner.setId(2);
        userOwner.setName("Ezequiel Guilherme");
        userOwner.setEmail("zeki-san@hotmail.com");
        userOwner.setLogged(true);
        userOwner.setPhoto("me");
        userOwner.setType(0);
        userOwner.setOwner(owner);
        realm.commitTransaction();
    }
}
