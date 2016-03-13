package zekisanmobile.petsitter.Sitter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Adapters.ContactListAdapter;
import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Handlers.GetContactsHandler;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;
import zekisanmobile.petsitter.R;

public class SitterHomeActivity extends AppCompatActivity
        implements RecyclerViewOnClickListenerHack, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout_sitter_home) DrawerLayout drawer;
    @Bind(R.id.toolbar_sitter_home) Toolbar toolbar;
    @Bind(R.id.nav_view_sitter_home) NavigationView navigationView;
    @Bind(R.id.rv_list_received_contacts) RecyclerView recyclerView;
    private User user;
    private ContactListAdapter adapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_home);

        ButterKnife.bind(this);

        user = UserDAO.getLoggedUser(1);
        contacts = new ArrayList<Contact>();

        configureToolbar();
        configureNavigationDrawer();
        configureNavigationView();

        configureAdapter();
        configureRecyclerView();
        new GetContactsHandler(this).execute(String.valueOf(user.getSitter().getApiId()));
    }

    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        Context context = getApplicationContext();
        ImageView ivUserImage = (ImageView) header.findViewById(R.id.ivUserImage);
        ivUserImage.setImageResource(context.getResources()
                .getIdentifier(user.getPhoto(), "drawable", context.getPackageName()));

        TextView tvUsername = (TextView) header.findViewById(R.id.tvUsername);
        tvUsername.setText(user.getName());

        TextView tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(user.getEmail());
    }

    private void configureNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void configureAdapter() {
        adapter = new ContactListAdapter(contacts, this);
        adapter.setRecyclerViewOnClickListenerHack(this);
    }

    private void configureToolbar() {
        toolbar.setTitle(user.getName());
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    public void updateAdapter(){
        this.contacts = ContactDAO.getAllContactsFromSitter(user.getSitter().getApiId());
        adapter.updateList(contacts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
