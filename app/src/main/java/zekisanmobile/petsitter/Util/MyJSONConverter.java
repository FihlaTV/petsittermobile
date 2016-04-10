package zekisanmobile.petsitter.Util;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.AnimalSitter;
import zekisanmobile.petsitter.Model.Sitter;

public class MyJSONConverter {

    public static ArrayList<Sitter> convertSitters(JSONArray jsonArray) {
        ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Sitter sitter = Sitter.insertOrUpdate(
                        jsonObject.getLong("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("address"),
                        jsonObject.getString("photo"),
                        jsonObject.getString("header_background"),
                        Float.parseFloat(jsonObject.getString("latitude")),
                        Float.parseFloat(jsonObject.getString("longitude")),
                        jsonObject.getString("district"),
                        Double.valueOf(jsonObject.getString("value_hour")),
                        Double.valueOf(jsonObject.getString("value_shift")),
                        Double.valueOf(jsonObject.getString("value_day")),
                        jsonObject.getString("about_me")
                );

                new Delete().from(AnimalSitter.class).where("sitter = ?", sitter.getId()).execute();

                JSONArray animalsArray = jsonObject.getJSONArray("animals");
                ActiveAndroid.beginTransaction();
                try {
                    for (int j = 0; j < animalsArray.length(); j++) {
                        JSONObject animalObject = animalsArray.getJSONObject(j);
                        Animal animal = Animal.load(Animal.class, animalObject.getLong("id"));
                        AnimalSitter animalSitter = new AnimalSitter(animal, sitter);
                        animalSitter.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
                returnedSitters.add(sitter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnedSitters;
    }

}
