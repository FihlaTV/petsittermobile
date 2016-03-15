package zekisanmobile.petsitter.Sitter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.R;

public class ContactDetailsActivity extends AppCompatActivity
        implements ContactDetailsView, OnMapReadyCallback {

    private ContactDetailsPresenter presenter;

    @Bind(R.id.toolbar_contact_details) Toolbar toolbar;
    @Bind(R.id.iv_contact_owner) ImageView ivContactOwner;
    @Bind(R.id.tv_contact_owner) TextView tvContactOwner;
    @Bind(R.id.tv_contact_district) TextView tvContactDistrict;
    @Bind(R.id.tv_contact_address) TextView tvContactAddress;
    @Bind(R.id.tv_contact_date_period) TextView tvContactDatePeriod;
    @Bind(R.id.tv_contact_time_period) TextView tvContactTimePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        ButterKnife.bind(this);

        presenter = new ContactDetailsPresenterImpl(this);
        presenter.getContactFromDb(getIntent().getLongExtra("contactId", 1L));

        configureToolbar();
        configureViews();
        configureMap();
    }

    private void configureMap() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void configureViews() {
        int imageId = getResources().getIdentifier("me", "drawable", getPackageName());
        ivContactOwner.setImageResource(imageId);
        tvContactOwner.setText(presenter.getContactOwnerName());
        tvContactDistrict.setText(presenter.getContactDistrict());
        tvContactAddress.setText(presenter.getContactAddress());
        tvContactDatePeriod.setText(presenter.getContactDatePeriod());
        tvContactTimePeriod.setText(presenter.getContactTimePeriod());
    }

    private void configureToolbar() {
        toolbar.setTitle("Solicitação de Pet Sitter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng =  new LatLng(presenter.getContactOwnerLatitude(), presenter.getContactOwnerLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        googleMap.animateCamera(cameraUpdate);
    }
}
