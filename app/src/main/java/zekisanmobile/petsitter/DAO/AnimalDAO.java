package zekisanmobile.petsitter.DAO;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Animal;

public class AnimalDAO {

    public static RealmResults<Animal> getAllAnimals(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Animal.class).findAll();
    }

}
