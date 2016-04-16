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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.Owner.OwnerHomeActivity;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Sitter.SitterHomeActivity;
import zekisanmobile.petsitter.model.AnimalModel;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.model.UserModel;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.vo.User;

public class MainActivity extends AppCompatActivity {

    @Inject
    UserModel userModel;

    @Inject
    AnimalModel animalModel;

    @Inject
    OwnerModel ownerModel;

    @Inject
    SitterModel sitterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.loginOwner)
    public void onLoginOwnerClick() {
        Intent intent = new Intent(MainActivity.this, OwnerHomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginPetsitter)
    public void onLoginPetsitterClick() {
        Intent intent = new Intent(MainActivity.this, SitterHomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        if (sharedPreferences.getInt("flag", 0) == 0) {
            Log.i("LOG", "init()");
            sharedPreferences.edit().putInt("flag", 1).apply();

            try {
                createUsers();
                createAnimals();
                createOwners();
                createSitters();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            List<Animal> animals = animalModel.all();
            for (Animal a : animals) {
                Log.i("LOG", "Animal: { id: " + a.getId() + ", name: " + a.getName() + " }");
            }
            List<Owner> owners = ownerModel.all();
            for (Owner o : owners) {
                Log.i("LOG", "Owner: { id: " + o.getId() + ", name: " + o.getName() + " }");
            }
            List<Sitter> sitters = sitterModel.all();
            for (Sitter s : sitters) {
                Log.i("LOG", "Sitter: { id: " + s.getId() + ", name: " + s.getName() + " }");
            }
            List<User> users = userModel.all();
            for (User u : users) {
                Log.i("LOG", "User: { id: " + u.getId() + ", name: " + u.getName() + " }");
            }
        }
    }

    private void createUsers() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("users.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            List<User> users = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                User user = new User();
                user.setName(userObject.getString("name"));
                user.setEmail(userObject.getString("email"));
                user.setPhoto(userObject.getString("photo"));
                user.setLogged(userObject.getBoolean("logged"));
                user.setType(userObject.getInt("type"));
                users.add(user);
            }
            userModel.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAnimals() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("animals.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            List<Animal> animals = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject animalObject = jsonArray.getJSONObject(i);
                Animal animal = new Animal();
                animal.setName(animalObject.getString("name"));
                animals.add(animal);
            }
            animalModel.saveAll(animals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOwners() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("owners.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            List<Owner> owners = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ownerObject = jsonArray.getJSONObject(i);
                Owner owner = new Owner();
                owner.setApiId(ownerObject.getLong("apiId"));
                owner.setName(ownerObject.getString("name"));
                owner.setAddress(ownerObject.getString("address"));
                owner.setDistrict(ownerObject.getString("district"));
                owner.setLatitude(Float.parseFloat(ownerObject.getString("latitude")));
                owner.setLongitude(Float.parseFloat(ownerObject.getString("longitude")));
                owner.setUser(userModel.find(ownerObject.getJSONObject("user").getLong("id")));
                owners.add(owner);
            }
            ownerModel.saveAll(owners);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSitters() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("sitters.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            List<Sitter> sitters = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject sitterObject = jsonArray.getJSONObject(i);
                Sitter sitter = new Sitter();
                sitter.setApiId(sitterObject.getLong("apiId"));
                sitter.setName(sitterObject.getString("name"));
                sitter.setAddress(sitterObject.getString("address"));
                sitter.setPhoto(sitterObject.getString("photo"));
                sitter.setProfileBackground(sitterObject.getString("profile_background"));
                sitter.setLatitude(Float.parseFloat(sitterObject.getString("latitude")));
                sitter.setLongitude(Float.parseFloat(sitterObject.getString("longitude")));
                sitter.setDistrict(sitterObject.getString("district"));
                sitter.setValue_hour(sitterObject.getDouble("value_hour"));
                sitter.setValue_shift(sitterObject.getDouble("value_shift"));
                sitter.setValue_day(sitterObject.getDouble("value_day"));
                sitter.setAbout_me(sitterObject.getString("about_me"));
                sitter.setUser(userModel.find(sitterObject.getJSONObject("user").getLong("id")));
                sitters.add(sitter);
            }
            sitterModel.saveAll(sitters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
