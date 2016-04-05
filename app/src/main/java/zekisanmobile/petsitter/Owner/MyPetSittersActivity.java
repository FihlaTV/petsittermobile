package zekisanmobile.petsitter.Owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.R;

public class MyPetSittersActivity extends AppCompatActivity implements MyPetSittersView{

    private MyPetSittersPresenter presenter;

    @Bind(R.id.toolbar_my_pet_sitters)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_sitters);

        ButterKnife.bind(this);

        presenter = new MyPetSittersPresenterImpl(this);

        configureToolbar();
    }

    private void configureToolbar() {
        toolbar.setTitle("Meus Pet Sitters");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
