package zekisanmobile.petsitter.view.main;

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
import io.realm.Realm;
import zekisanmobile.petsitter.view.owner.OwnerHomeActivity;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.view.sitter.SitterHomeActivity;
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

    @Inject
    Realm realm;

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
            List<Sitter> sitters = sitterModel.all(Realm.getDefaultInstance());
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
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(User.class, inputStream);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    private void createAnimals() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("animals.json");
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Animal.class, inputStream);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    private void createOwners() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("owners.json");
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Owner.class, inputStream);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    private void createSitters() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("sitters.json");
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Sitter.class, inputStream);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }
}
