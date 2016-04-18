package zekisanmobile.petsitter.view.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.R;

public class SitterProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar_sitter_profile) Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.image_sitter_profile) ImageView image_sitter_profile;
    @Bind(R.id.txtPetSitterDistrictValue) TextView txtPetSitterDistrictValue;
    @Bind(R.id.txtTitleValues) TextView txtTitleValues;
    @Bind(R.id.txtTitleHourValue) TextView txtTitleHourValue;
    @Bind(R.id.txtTitleShiftValue) TextView txtTitleShiftValue;
    @Bind(R.id.txtTitleDayValue) TextView txtTitleDayValue;
    @Bind(R.id.txtAboutMeText) TextView txtAboutMeText;

    private Sitter sitter;

    @Inject
    SitterModel sitterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_profile);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        long sitter_id = intent.getLongExtra("sitter_id", 0);
        sitter = sitterModel.find(sitter_id);

        configureToolbar();
        prepareSitterData();
    }

    @OnClick(R.id.fab_new_contact)
    public void onClick(){
        Intent intent = new Intent(SitterProfileActivity.this, NewContactActivity.class);
        intent.putExtra("sitter_id", sitter.getId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {
        collapsingToolbarLayout.setTitle(sitter.getName());

        int imageId = getResources().getIdentifier(sitter.getProfileBackground(),
                "drawable", getPackageName());
        image_sitter_profile.setImageResource(imageId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void prepareSitterData() {
        txtPetSitterDistrictValue.setText(sitter.getDistrict());
        txtTitleValues.setText("Valores");
        txtTitleHourValue.setText(NumberFormat.getCurrencyInstance()
                .format(sitter.getValue_hour()));
        txtTitleShiftValue.setText(NumberFormat.getCurrencyInstance()
                .format(sitter.getValue_shift()));
        txtTitleDayValue.setText(NumberFormat.getCurrencyInstance().format(sitter.getValue_day()));
        txtAboutMeText.setText(sitter.getAbout_me());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
