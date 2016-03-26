package zekisanmobile.petsitter.DAO;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Sitter;

public class AnimalDAO {

    public static RealmResults<Animal> getAllAnimals(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Animal.class).findAll();
    }

    public static RealmResults<Animal> getAnimalsFromSitter(Sitter sitter){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Animal> animals = realm.allObjects(Animal.class);
        long[] ids = new long[sitter.getAnimals().size()];
        for(int i = 0; i < sitter.getAnimals().size(); i++){
            ids[i] = sitter.getAnimals().get(i).getId();
        }
        RealmQuery query = animals.where();
        for (long id : ids) {
            if (ids.length > 1) query = query.or();
            query = query.equalTo("id", id);
        }
            return query.findAll();
    }

    public static void createAnimal(String name){
        Realm realm = Realm.getDefaultInstance();
        int id;
        try {
            id = realm.where(Animal.class).findAll().size();
        }catch (NullPointerException e){
            id = 0;
        }

        realm.beginTransaction();
        Animal animal = realm.createObject(Animal.class);
        animal.setId(id + 1);
        animal.setName(name);
        realm.commitTransaction();
        realm.close();
    }
}
