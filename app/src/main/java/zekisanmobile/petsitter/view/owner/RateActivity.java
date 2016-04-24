package zekisanmobile.petsitter.view.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;

public class RateActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_rate)
    Toolbar toolbar;

    @Inject
    ContactModel contactModel;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        long contact_id = intent.getLongExtra("contact_id", 0);
        contact = contactModel.find(Realm.getDefaultInstance(), contact_id);

        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Avaliação de Pet Sitter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }
}
