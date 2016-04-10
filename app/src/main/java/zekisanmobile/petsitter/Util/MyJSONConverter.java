package zekisanmobile.petsitter.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Sitter;

public class MyJSONConverter {

    public static ArrayList<Sitter> convertSitters(JSONArray jsonArray) {
        ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray animalsArray = jsonObject.getJSONArray("animals");
                List<Animal> animals = new ArrayList<>();
                for (int j = 0; j < animalsArray.length(); j++) {
                    JSONObject animalObject = animalsArray.getJSONObject(j);
                    Animal animal = new Animal();
                    animal.name = animalObject.getString("name");
                    animals.add(animal);
                }

                Sitter sitter = new Sitter();
                sitter.apiId = jsonObject.getLong("id");
                sitter.name = jsonObject.getString("name");
                sitter.address = jsonObject.getString("address");
                sitter.photo = jsonObject.getString("photo");
                sitter.profileBackground = jsonObject.getString("header_background");
                sitter.latitude = Float.parseFloat(jsonObject.getString("latitude"));
                sitter.longitude = Float.parseFloat(jsonObject.getString("longitude"));
                sitter.district = jsonObject.getString("district");
                sitter.value_hour = Double.valueOf(jsonObject.getString("value_hour"));
                sitter.value_shift = Double.valueOf(jsonObject.getString("value_shift"));
                sitter.value_day = Double.valueOf(jsonObject.getString("value_day"));
                sitter.about_me = jsonObject.getString("about_me");
                sitter.animals = animals;
                returnedSitters.add(sitter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnedSitters;
    }

}
