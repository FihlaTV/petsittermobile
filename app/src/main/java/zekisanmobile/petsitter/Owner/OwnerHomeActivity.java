package zekisanmobile.petsitter.Owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zekisanmobile.petsitter.Adapters.ViewPagerAdapter;
import zekisanmobile.petsitter.Fragments.MapsFragment;
import zekisanmobile.petsitter.Fragments.SearchFragment;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.Model.Sitter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        ButterKnife.bind(this);

        presenter = new OwnerHomePresenterImpl(this);

        configureToolbar();
        configureTabLayout();
        configureNavigationDrawer();
        configureNavigationView();
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
}
