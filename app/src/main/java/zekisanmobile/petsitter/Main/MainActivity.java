package zekisanmobile.petsitter.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Owner;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;
import zekisanmobile.petsitter.Owner.OwnerHomeActivity;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Sitter.SitterHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
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

        Realm realm = Realm.getDefaultInstance();
        init(realm);
    }

    private void init(Realm realm){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        if(sharedPreferences.getInt("flag", 0) == 0){
            Log.i("LOG", "init()");
            sharedPreferences.edit().putInt("flag", 1).apply();

            try{
                AssetManager assetManager = getAssets();

                realm.beginTransaction();
                InputStream inputStream = assetManager.open("animals.json");
                realm.createOrUpdateAllFromJson(Animal.class, inputStream);
                realm.commitTransaction();

                realm.beginTransaction();
                inputStream = assetManager.open("owners.json");
                realm.createOrUpdateAllFromJson(Owner.class, inputStream);
                realm.commitTransaction();

                realm.beginTransaction();
                inputStream = assetManager.open("sitters.json");
                realm.createOrUpdateAllFromJson(Sitter.class, inputStream);
                realm.commitTransaction();

                realm.beginTransaction();
                inputStream = assetManager.open("users.json");
                realm.createOrUpdateAllFromJson(User.class, inputStream);
                realm.commitTransaction();
            }catch (Exception e) {
                e.printStackTrace();
                realm.cancelTransaction();
            }
        } else {
            RealmResults<Animal> animals = realm.where(Animal.class).findAll();
            for(Animal a: animals){
                Log.i("LOG", "Animal: { id: " + a.getId() + ", name: " + a.getName() + " }");
            }
            RealmResults<Owner> owners = realm.where(Owner.class).findAll();
            for(Owner o: owners){
                Log.i("LOG", "Owner: { id: " + o.getId() + ", name: " + o.getName() + " }");
            }
            RealmResults<Sitter> sitters = realm.where(Sitter.class).findAll();
            for(Sitter s: sitters){
                Log.i("LOG", "Sitter: { id: " + s.getId() + ", name: " + s.getName() + " }");
            }
            RealmResults<User> users = realm.where(User.class).findAll();
            for(User u: users){
                Log.i("LOG", "User: { id: " + u.getId() + ", name: " + u.getName() + " }");
            }
        }
    }

}
