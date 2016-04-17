package zekisanmobile.petsitter.Sitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zekisanmobile.petsitter.Adapters.ViewPagerAdapter;
import zekisanmobile.petsitter.Fragments.ContactsByStatusFragment;
import zekisanmobile.petsitter.Main.MainActivity;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.controller.ContactController;
import zekisanmobile.petsitter.event.contact.FetchedSitterContactsEvent;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.vo.Contact;

public class SitterHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SitterHomeView{

    @Bind(R.id.drawer_layout_sitter_home) DrawerLayout drawer;
    @Bind(R.id.toolbar_sitter_home) Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.nav_view_sitter_home) NavigationView navigationView;
    @Bind(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;

    @Inject
    ContactController controller;

    private ViewPagerAdapter adapter;
    private SitterHomePresenter presenter;

    private ContactsByStatusFragment newContactsFragment;
    private ContactsByStatusFragment currentContactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_home);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        this.presenter = new SitterHomePresenterImpl(this);

        configureToolbar();
        configureTabLayout();
        configureNavigationDrawer();
        configureNavigationView();

        updateAdapters(presenter.getNewContacts(), presenter.getCurrentContacts());
    }

    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
        controller.fetchSitterContactsAsync(true, presenter.getLoggedUserSitterApiId());
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
                .getIdentifier(presenter.getLoggedUserPhoto(), "drawable",
                        getApplicationContext().getPackageName()));

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
        int itemId = item.getItemId();
        Intent intent;
        switch (itemId) {
            case R.id.home:
                intent = new Intent(SitterHomeActivity.this, SitterHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nextContacts:
                intent = new Intent(SitterHomeActivity.this, OtherContactsActivity.class);
                intent.putExtra("contacts_type", "next");
                intent.putExtra("sitter_id", presenter.getLoggedUserSitterId());
                startActivity(intent);
                break;
            case R.id.finishedContacts:
                intent = new Intent(SitterHomeActivity.this, OtherContactsActivity.class);
                intent.putExtra("contacts_type", "finished");
                intent.putExtra("sitter_id", presenter.getLoggedUserSitterId());
                startActivity(intent);
                break;
            case R.id.logOut:
                intent = new Intent(SitterHomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void updateAdapters(List<Contact> newContacts, List<Contact> currentContacts) {
        newContactsFragment.updateContactsList(newContacts);
        currentContactsFragment.updateContactsList(currentContacts);
    }

    @Override
    public PetSitterApp getPetSitterApp() {
        return (PetSitterApp) getApplication();
    }

    @Subscribe
    public void onEventMainThread(FetchedSitterContactsEvent event) {
        if (event.isSuccess()) {
            updateAdapters(presenter.getNewContacts(), presenter.getCurrentContacts());
        } else {
            Snackbar.make(coordinatorLayout, "Não foi possível atualizar a lista de " +
                    "solicitações de Pet Sitter", Snackbar.LENGTH_SHORT).show();
        }
    }
}
