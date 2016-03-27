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

        User user = realm.where(User.class)
                .equalTo("logged", true)
                .equalTo("type", userType)
                .findFirst();

        return user;
    }
}
