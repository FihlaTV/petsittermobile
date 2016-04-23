package zekisanmobile.petsitter.view.sitter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.api.SendContactStatusBody;
import zekisanmobile.petsitter.controller.contact.ContactController;

public class ContactDetailsActivity extends AppCompatActivity
        implements ContactDetailsView, OnMapReadyCallback {

    private ContactDetailsPresenter presenter;
    private long contact_id;

    @Inject
    ContactController controller;

    @Bind(R.id.toolbar_contact_details)
    Toolbar toolbar;
    @Bind(R.id.iv_contact_owner)
    ImageView ivContactOwner;
    @Bind(R.id.tv_contact_owner)
    TextView tvContactOwner;
    @Bind(R.id.tv_contact_district)
    TextView tvContactDistrict;
    @Bind(R.id.tv_contact_address)
    TextView tvContactAddress;
    @Bind(R.id.tv_contact_date_period)
    TextView tvContactDatePeriod;
    @Bind(R.id.tv_contact_time_period)
    TextView tvContactTimePeriod;
    @Bind(R.id.lv_contact_pets)
    ListView lvContactPets;
    @Bind(R.id.tv_total_value)
    TextView tvTotalValue;
    @Bind(R.id.bt_accept)
    Button btAccept;
    @Bind(R.id.bt_reject)
    Button btReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        presenter = new ContactDetailsPresenterImpl(this);
        presenter.getContactFromDb(getIntent().getLongExtra("contactId", 1L));
        contact_id = presenter.getContactApiId();

        configureToolbar();
        configureViews();
        configureButtonsVisibility();
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
        tvTotalValue.setText(presenter.getContactTotalValue());

        ArrayAdapter<String> animalsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenter.getContactAnimals());
        lvContactPets.setAdapter(animalsAdapter);

        int totalHeight = 0;
        for (int i = 0; i < animalsAdapter.getCount(); i++) {
            View listItem = animalsAdapter.getView(i, null, lvContactPets);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams listViewParams = lvContactPets.getLayoutParams();
        listViewParams.height = totalHeight + (lvContactPets.getDividerHeight() * (lvContactPets.getChildCount() - 1));
        lvContactPets.requestLayout();
    }

    private void configureButtonsVisibility(){
        if (presenter.isAcceptedOrRejectedOrFinished()){
            ((ViewGroup) btAccept.getParent()).removeView(btAccept);
            ((ViewGroup) btReject.getParent()).removeView(btReject);
        }
    }

    private void configureToolbar() {
        toolbar.setTitle("Solicitação de Pet Sitter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(presenter.getContactOwnerLatitude(), presenter.getContactOwnerLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        googleMap.animateCamera(cameraUpdate);
    }

    @OnClick(R.id.bt_accept)
    public void acceptContact(){
        presenter.acceptContact();
        SendContactStatusBody body = new SendContactStatusBody();
        body.setStatus(30);
        controller.sendContactUpdateAsync(true, presenter.getContactApiId(), body);
        showAcceptDialog();
    }

    @OnClick(R.id.bt_reject)
    public void rejectContact() {
        showRejectDialog();
    }

    private void showAcceptDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Parabéns! Seu novo trabalho iniciará dia "
                        + presenter.getContactStartDate() + ".")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ContactDetailsActivity.this, SitterHomeActivity.class);
                        startActivity(intent);
                    }
                }).create();
        dialog.show();
        keepDialog(dialog);
    }

    private void showRejectDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Recusar Solicitação de Pet Sitter?")
                .setPositiveButton("RECUSAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteContact();
                        SendContactStatusBody body = new SendContactStatusBody();
                        body.setStatus(20);
                        controller.sendContactUpdateAsync(true, contact_id, body);
                        Intent intent = new Intent(ContactDetailsActivity.this, SitterHomeActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCELAR", null)
                .create();
        dialog.show();
        keepDialog(dialog);
    }

    private void keepDialog(Dialog dialog){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public PetSitterApp getPetSitterApp() {
        return (PetSitterApp) getApplication();
    }
}
