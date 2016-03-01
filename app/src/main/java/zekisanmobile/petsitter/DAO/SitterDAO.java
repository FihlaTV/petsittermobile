package zekisanmobile.petsitter.DAO;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Sitter;

public class SitterDAO {

    public static RealmResults<Sitter> getAllSitters(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Sitter.class).findAll();
    }

    public static Sitter findSitter(long apiId){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Sitter.class).equalTo("apiId", apiId).findFirst();
    }

    public static Sitter insertOrUpdateSitter(long apiId, String name, String address, String photo,
                                              String profile_background, float latitude, float longitude,
                                              String district, double value_hour, double value_shift,
                                              double value_day, String about_me){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Sitter newSitter;

        if((newSitter = findSitter(apiId)) == null) {
            newSitter = realm.createObject(Sitter.class);
            newSitter.setId(getAllSitters().size() + 1);
        }

        newSitter.setApiId(apiId);
        newSitter.setName(name);
        newSitter.setAddress(address);
        newSitter.setPhoto(photo);
        newSitter.setProfile_background(profile_background);
        newSitter.setLatitude(latitude);
        newSitter.setLongitude(longitude);
        newSitter.setDistrict(district);
        newSitter.setValue_hour(value_hour);
        newSitter.setValue_shift(value_shift);
        newSitter.setValue_day(value_day);
        newSitter.setAbout_me(about_me);

        realm.commitTransaction();

        return newSitter;
    }
}
