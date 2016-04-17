package zekisanmobile.petsitter.util;

public class MyJSONConverter {

    /*public static ArrayList<Sitter> convertSitters(JSONArray jsonArray) {
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
                        jsonObject.getDouble("value_hour"),
                        jsonObject.getDouble("value_shift"),
                        jsonObject.getDouble("value_day"),
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
                } finally {
                    ActiveAndroid.endTransaction();
                }
                returnedSitters.add(sitter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnedSitters;
    }

    public static void convertContacts(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject sitterObject = jsonObject.getJSONObject("sitter");

                Sitter sitter = Sitter.insertOrUpdate(
                        sitterObject.getLong("id"),
                        sitterObject.getString("name"),
                        sitterObject.getString("address"),
                        sitterObject.getString("photo"),
                        sitterObject.getString("header_background"),
                        Float.parseFloat(sitterObject.getString("latitude")),
                        Float.parseFloat(sitterObject.getString("longitude")),
                        sitterObject.getString("district"),
                        sitterObject.getDouble("value_hour"),
                        sitterObject.getDouble("value_shift"),
                        sitterObject.getDouble("value_day"),
                        sitterObject.getString("about_me")
                );

                JSONObject ownerObject = jsonObject.getJSONObject("pet_owner");
                Owner owner = Owner.insertOrUpdate(ownerObject.getLong("id"),
                        ownerObject.getString("name"),
                        ownerObject.getString("address"),
                        ownerObject.getString("district"),
                        Float.parseFloat(ownerObject.getString("latitude")),
                        Float.parseFloat(ownerObject.getString("longitude")));

                JSONArray animalsArray = jsonObject.getJSONArray("animals");
                List<Animal> animals = new ArrayList<>();

                for (int j = 0; j < animalsArray.length(); j++) {
                    JSONObject animalObject = animalsArray.getJSONObject(j);
                    Animal animal = Animal.load(Animal.class, animalObject.getLong("id"));
                    animals.add(animal);
                }

                Contact.insertOrUpdate(jsonObject.getLong("id"),
                        jsonObject.getString("date_start"),
                        jsonObject.getString("date_final"),
                        jsonObject.getString("time_start"),
                        jsonObject.getString("time_final"),
                        jsonObject.getString("created_at").substring(0, 10),
                        sitter, owner, Double.parseDouble(jsonObject.getString("total_value")),
                        Integer.parseInt(jsonObject.getString("status_cd")), animals);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
