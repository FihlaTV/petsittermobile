package zekisanmobile.petsitter.Sitter;

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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zekisanmobile.petsitter.Adapters.ViewPagerAdapter;
import zekisanmobile.petsitter.Fragments.ContactsByStatusFragment;
import zekisanmobile.petsitter.Fragments.MapsFragment;
import zekisanmobile.petsitter.Fragments.SearchFragment;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.R;

public class SitterHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SitterHomeView{

    @Bind(R.id.drawer_layout_sitter_home) DrawerLayout drawer;
    @Bind(R.id.toolbar_sitter_home) Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.nav_view_sitter_home) NavigationView navigationView;

    private ViewPagerAdapter adapter;
    private SitterHomePresenter presenter;

    private ContactsByStatusFragment newContactsFragment;
    private ContactsByStatusFragment currentContactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_home);

        ButterKnife.bind(this);

        this.presenter = new SitterHomePresenterImpl(this);

        configureToolbar();
        configureTabLayout();
        configureNavigationDrawer();
        configureNavigationView();

        presenter.getContacts();
    }

    @Override
    protected void onDestroy(){
        presenter.onDestroy();
        super.onDestroy();
    }

    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        CircleImageView ivUserImage = ButterKnife.findById(header, R.id.ivUserImage);
        ivUserImage.setImageResource(getApplicationContext().getResources()
                .getIdentifier(presenter.getLoggedUserPhoto(), "drawable", getApplicationContext().getPackageName()));

        TextView tvUsername = ButterKnife.findById(header, R.id.tvUsername);
        tvUsername.setText(presenter.getLoggedUserName());

        TextView tvUserEmail = ButterKnife.findById(header, R.id.tvUserEmail);
        tvUserEmail.setText(presenter.getLoggedUserEmail());
    }

    private void configureTabLayout() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void configureNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureToolbar() {
        toolbar.setTitle(presenter.getLoggedUserName());
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        newContactsFragment = new ContactsByStatusFragment();
        currentContactsFragment = new ContactsByStatusFragment();
        adapter.addFragment(newContactsFragment, "NOVOS");
        adapter.addFragment(currentContactsFragment, "EM ANDAMENTO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void updateAdapters(List<Contact> newContacts, List<Contact> currentContacts) {
        newContactsFragment.updateContactsList(newContacts);
        currentContactsFragment.updateContactsList(currentContacts);
    }
}
