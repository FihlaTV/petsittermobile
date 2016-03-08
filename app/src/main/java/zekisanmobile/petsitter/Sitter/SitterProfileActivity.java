package zekisanmobile.petsitter.Sitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Owner.NewContactActivity;
import zekisanmobile.petsitter.R;

public class SitterProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Sitter sitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_profile);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("SITTER");

        configureToolbar();
        prepareSitterData();

        FloatingActionButton fab_new_contact = (FloatingActionButton) findViewById(R.id.fab_new_contact);
        fab_new_contact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SitterProfileActivity.this, NewContactActivity.class);
                intent.putExtra("sitter", sitter);
                startActivity(intent);
            }
        });
    }

    private void configureToolbar() {
        // collapsingToolbarLayout
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(sitter.getName());

        ImageView image_sitter_profile = (ImageView) findViewById(R.id.image_sitter_profile);
        int imageId = getResources().getIdentifier(sitter.getProfile_background(), "drawable", getPackageName());
        image_sitter_profile.setImageResource(imageId);

        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar_sitter_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void prepareSitterData() {
        TextView txtPetSitterDistrictValue = (TextView) findViewById(R.id.txtPetSitterDistrictValue);
        txtPetSitterDistrictValue.setText(sitter.getDistrict());

        TextView txtTitleValues = (TextView) findViewById(R.id.txtTitleValues);
        txtTitleValues.setText("Valores");

        TextView txtTitleHourValue = (TextView) findViewById(R.id.txtTitleHourValue);
        txtTitleHourValue.setText(NumberFormat.getCurrencyInstance().format(sitter.getValue_hour()));

        TextView txtTitleShiftValue = (TextView) findViewById(R.id.txtTitleShiftValue);
        txtTitleShiftValue.setText(NumberFormat.getCurrencyInstance().format(sitter.getValue_shift()));

        TextView txtTitleDayValue = (TextView) findViewById(R.id.txtTitleDayValue);
        txtTitleDayValue.setText(NumberFormat.getCurrencyInstance().format(sitter.getValue_day()));

        TextView txtAboutMeText = (TextView) findViewById(R.id.txtAboutMeText);
        txtAboutMeText.setText(sitter.getAbout_me());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
