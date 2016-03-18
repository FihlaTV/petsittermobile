package zekisanmobile.petsitter.Owner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Adapters.ViewPagerAdapter;
import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Fragments.MapsFragment;
import zekisanmobile.petsitter.Fragments.SearchFragment;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Handlers.JSONResponseHandler;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;
import zekisanmobile.petsitter.R;

public class OwnerHomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, OwnerHomeView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.viewpager) ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private SitterFragment sitterFragment;
    private MapsFragment mapsFragment;
    private TabLayout tabLayout;

    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";

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

        if (savedInstanceState == null) {
            new JSONResponseHandler(sitterFragment, this).execute(API_SEARCH_URL);
        }
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
        ImageView ivUserImage = (ImageView) header.findViewById(R.id.ivUserImage);
        ivUserImage.setImageResource(context.getResources()
                .getIdentifier(presenter.getLoggedUserPhoto(), "drawable", context.getPackageName()));

        TextView tvUsername = (TextView) header.findViewById(R.id.tvUsername);
        tvUsername.setText(presenter.getLoggedUserName());

        TextView tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(presenter.getLoggedUserEmail());
    }

    private void configureTabLayout() {
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
