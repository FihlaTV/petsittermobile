package zekisanmobile.petsitter.model;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.User;

public class UserModel {

    @Inject
    Realm realm;

    public void save(User user){
        user.validate();
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public void saveAll(final List<User> users){
        ValidationUtil.pruneInvalid(users);
        if (users.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(users);
        realm.commitTransaction();
    }

    public User find(long id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    public List<User> all(){
        return realm.where(User.class).findAll();
    }
}
