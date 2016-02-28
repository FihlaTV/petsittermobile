package zekisanmobile.petsitter.DAO;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Owner;
import zekisanmobile.petsitter.Model.User;

public class UserDAO {

    public static User getLoggedUser(){
        Realm realm = Realm.getDefaultInstance();
        Log.d(UserDAO.class.getSimpleName(), "Versão: " + realm.getVersion());
        RealmResults<User> allUsers = realm.where(User.class).findAll();
        RealmResults<Owner> allOwners = realm.where(Owner.class).findAll();
        User user;

        if (allUsers.size() > 0) {
            user = realm.where(User.class).equalTo("logged", true).findAll().get(0);
            realm.beginTransaction();
            user.setId(1);
            realm.commitTransaction();
        }
        else{
            realm.beginTransaction();
            user = realm.createObject(User.class);
            user.setId(1);
            user.setName("Ezequiel Guilherme");
            user.setEmail("zeki-san@hotmail.com");
            user.setPhoto("me");
            user.setLogged(true);
            user.setType(0);
            realm.commitTransaction();
        }

        if(allOwners.isEmpty()){
            realm.beginTransaction();
            Owner owner = realm.createObject(Owner.class);
            owner.setId(1);
            owner.setApiId(1);
            owner.setNome("Ezequiel Guilherme");
            user.setOwner(owner);
            realm.commitTransaction();
        }

        return user;
    }

}
