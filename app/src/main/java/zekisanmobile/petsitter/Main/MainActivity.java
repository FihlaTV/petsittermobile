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
            }catch (Exception e) {
                e.printStackTrace();
                realm.cancelTransaction();
            }
        } else {
            RealmResults<Animal> animals = realm.where(Animal.class).findAll();
            for(Animal a: animals){
                Log.i("LOG", "Animal: { id: " + a.getId() + ", name: " + a.getName() + " }");
            }
        }
    }

}
