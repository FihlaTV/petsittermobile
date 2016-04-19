package zekisanmobile.petsitter.view.owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zekisanmobile.petsitter.adapter.ViewPagerAdapter;
import zekisanmobile.petsitter.event.contact.SaveFetchedContactsEvent;
import zekisanmobile.petsitter.fragment.MapsFragment;
import zekisanmobile.petsitter.fragment.SearchFragment;
import zekisanmobile.petsitter.fragment.SitterFragment;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.view.main.MainActivity;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.controller.contact.ContactController;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.R;

public class OwnerHomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, OwnerHomeView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) TabLayout tabLayout;

    private ViewPagerAdapter adapter;
    private SitterFragment sitterFragment;
    private MapsFragment mapsFragment;

    private OwnerHomePresenter presenter;

    @Inject
    ContactController controller;

    @Inject
    ContactModel contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        presenter = new OwnerHomePresenterImpl(this);

        configureToolbar();
        configureTabLayout();
        configureNavigationDrawer();
        configureNavigationView();
    }

    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
        controller.fetchOwnerContactsAsync(false, presenter.getOwnerApiId());
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void configureNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        Context context = getApplicationContext();
        CircleImageView ivUserImage = (CircleImageView) header.findViewById(R.id.ivUserImage);
        ivUserImage.setImageResource(context.getResources()
                .getIdentifier(presenter.getLoggedUserPhoto(), "drawable", context.getPackageName()));

        TextView tvUsername = (TextView) header.findViewById(R.id.tvUsername);
        tvUsername.setText(presenter.getLoggedUserName());

        TextView tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(presenter.getLoggedUserEmail());
    }

    private void configureTabLayout() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
    }

    private void configureToolbar() {
        toolbar.setTitle(presenter.getLoggedUserName());
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        sitterFragment = new SitterFragment();
        mapsFragment = new MapsFragment();
        adapter.addFragment(new SearchFragment(), "PESQUISA");
        adapter.addFragment(sitterFragment, "PET SITTERS");
        adapter.addFragment(mapsFragment, "MAPA");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void updateSitterList(ArrayList<Sitter> sitters){
        sitterFragment.updateSittersList(sitters);
        mapsFragment.setSitters(sitters);
        mapsFragment.clearAllMarkers();
        mapsFragment.updateMapMarkers();
        tabLayout.getTabAt(1).select();
    }

    @Override
    public PetSitterApp getPetSitterApp() {
        return (PetSitterApp) getApplication();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.contacts:
                intent = new Intent(OwnerHomeActivity.this, MyPetSittersActivity.class);
                startActivity(intent);
                break;
            case R.id.logOut:
                intent = new Intent(OwnerHomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SaveFetchedContactsEvent event) {
        if (event.isSuccess()) {
            List<Contact> contacts = event.getContacts();
            for (Contact contact : contacts) {
                contactModel.insertOrUpdateContact(contact.getId(), contact.getDateStart(),
                        contact.getDateFinal(), contact.getTimeStart(), contact.getTimeFinal(),
                        contact.getCreatedAt(), contact.getSitter(), contact.getOwner(),
                        contact.getTotalValue(), contact.getStatus(), contact.getAnimals());
            }
        }
    }
}
