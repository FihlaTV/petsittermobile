package zekisanmobile.petsitter.Sitter;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import zekisanmobile.petsitter.Adapters.ContactListAdapter;
import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Handlers.GetContactsHandler;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.User;
import zekisanmobile.petsitter.R;

public class SitterHomeActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private User user;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_home);

        user = UserDAO.getLoggedUser(1);
        contacts = new ArrayList<Contact>();
        configureToolbar();
        configureAdapter();
        configureRecyclerView();
        new GetContactsHandler(this).execute(String.valueOf(user.getSitter().getApiId()));
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_received_contacts);

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
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(user.getSitter().getName());

        ImageView image_sitter_profile = (ImageView) findViewById(R.id.image_sitter_home);
        int imageId = getResources().getIdentifier("header_background_2", "drawable", getPackageName());
        image_sitter_profile.setImageResource(imageId);

        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar_sitter_home);
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
}
