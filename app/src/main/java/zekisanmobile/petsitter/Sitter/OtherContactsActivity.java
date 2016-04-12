package zekisanmobile.petsitter.Sitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Adapters.ContactListAdapter;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.R;

public class OtherContactsActivity extends AppCompatActivity implements OtherContactsView,
        RecyclerViewOnClickListenerHack {

    private OtherContactsPresenter presenter;
    private ContactListAdapter adapter;
    private String contactsType;
    private long sitter_id;

    @Bind(R.id.toolbar_other_contacts)
    Toolbar toolbar;
    @Bind(R.id.rv_others_contacts_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_contacts);

        ButterKnife.bind(this);
        this.contactsType = getIntent().getStringExtra("contacts_type");
        this.sitter_id = getIntent().getLongExtra("sitter_id", 0);
        presenter = new OtherContactsPresenterImpl(this, sitter_id);

        configureToolbar();
        configureRecyclerView();
    }

    private void configureToolbar() {
        toolbar.setTitle(presenter.getTitle(contactsType));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void configureRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        adapter = new ContactListAdapter(presenter.getContacts(contactsType), this);
        adapter.notifyDataSetChanged();
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(OtherContactsActivity.this, ContactDetailsActivity.class);
        intent.putExtra("contactId", adapter.getContactAtPosition(position).getId());
        startActivity(intent);
    }
}
