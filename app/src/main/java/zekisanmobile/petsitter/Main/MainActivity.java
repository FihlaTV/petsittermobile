package zekisanmobile.petsitter.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.model.Animal;
import zekisanmobile.petsitter.model.Owner;
import zekisanmobile.petsitter.model.Sitter;
import zekisanmobile.petsitter.model.User;
import zekisanmobile.petsitter.Owner.OwnerHomeActivity;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Sitter.SitterHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.loginOwner)
    public void onLoginOwnerClick(){
        Intent intent = new Intent(MainActivity.this, OwnerHomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginPetsitter)
    public void onLoginPetsitterClick(){
        Intent intent = new Intent(MainActivity.this, SitterHomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void init(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        if(sharedPreferences.getInt("flag", 0) == 0){
            Log.i("LOG", "init()");
            sharedPreferences.edit().putInt("flag", 1).apply();

            try{
                createAnimals();
                createOwners();
                createSitters();
                createUsers();
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            List<Animal> animals = Animal.all();
            for(Animal a: animals){
                Log.i("LOG", "Animal: { id: " + a.getId() + ", name: " + a.name + " }");
            }
            List<Owner> owners = Owner.all();
            for(Owner o: owners){
                Log.i("LOG", "Owner: { id: " + o.getId() + ", name: " + o.name + " }");
            }
            List<Sitter> sitters = Sitter.all();
            for(Sitter s: sitters){
                Log.i("LOG", "Sitter: { id: " + s.getId() + ", name: " + s.name + " }");
            }
            List<User> users = User.all();
            for(User u: users){
                Log.i("LOG", "User: { id: " + u.getId() + ", name: " + u.name + " }");
            }
        }
    }

    private void createUsers() {
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("users.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                User.create(
                        userObject.getString("name"),
                        userObject.getString("email"),
                        userObject.getString("photo"),
                        userObject.getBoolean("logged"),
                        userObject.getInt("type"),
                        Owner.load(Owner.class, 1),
                        Sitter.load(Sitter.class, 1)
                        );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAnimals() {
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("animals.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject animalObject = jsonArray.getJSONObject(i);
                Animal.create(animalObject.getString("name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOwners() {
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("owners.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ownerObject = jsonArray.getJSONObject(i);
                Owner.insertOrUpdate(
                        Long.parseLong(ownerObject.getString("apiId")),
                        ownerObject.getString("name"),
                        ownerObject.getString("address"),
                        ownerObject.getString("district"),
                        Float.parseFloat(ownerObject.getString("latitude")),
                        Float.parseFloat(ownerObject.getString("longitude"))
                );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSitters() {
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("sitters.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject sitterObject = jsonArray.getJSONObject(i);
                Sitter.insertOrUpdate(
                        Long.parseLong(sitterObject.getString("apiId")),
                        sitterObject.getString("name"),
                        sitterObject.getString("address"),
                        sitterObject.getString("photo"),
                        sitterObject.getString("profile_background"),
                        Float.parseFloat(sitterObject.getString("latitude")),
                        Float.parseFloat(sitterObject.getString("longitude")),
                        sitterObject.getString("district"),
                        sitterObject.getDouble("value_hour"),
                        sitterObject.getDouble("value_shift"),
                        sitterObject.getDouble("value_day"),
                        sitterObject.getString("about_me")
                );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
