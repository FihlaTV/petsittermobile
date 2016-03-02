package zekisanmobile.petsitter.DAO;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Owner;

public class OwnerDAO {

    public static RealmResults<Owner> getAllOwners(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Owner.class).findAll();
    }

    public static Owner findOwner(long apiId){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Owner.class).equalTo("apiId", apiId).findFirst();
    }

    public static Owner insertOrUpdateOwner(long apiId, String nome){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Owner newOwner;

        if((newOwner = findOwner(apiId)) == null) {
            newOwner = realm.createObject(Owner.class);
            newOwner.setId(getAllOwners().size() + 1);
        }

        newOwner.setApiId(apiId);
        newOwner.setNome(nome);

        realm.commitTransaction();

        return newOwner;
    }

}