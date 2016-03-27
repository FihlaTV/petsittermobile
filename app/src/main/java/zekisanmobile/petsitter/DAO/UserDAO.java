package zekisanmobile.petsitter.DAO;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Owner;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;

public class UserDAO {

    public static User getLoggedUser(int userType){
        Realm realm = Realm.getDefaultInstance();
        Log.d(UserDAO.class.getSimpleName(), "Versão: " + realm.getVersion());
        RealmResults<User> allUsers = realm.where(User.class).findAll();

        if (allUsers.isEmpty()) {
            createInitialData();
        }

        User user = realm.where(User.class)
                .equalTo("logged", true)
                .equalTo("type", userType)
                .findFirst();

        return user;
    }

    private static void createInitialData() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Owner owner = realm.createObject(Owner.class);
        owner.setId(1);
        owner.setApiId(1);
        owner.setName("Ezequiel Guilherme");
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
