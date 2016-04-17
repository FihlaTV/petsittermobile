package zekisanmobile.petsitter.view.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.adapter.OwnerContactListAdapter;
import zekisanmobile.petsitter.interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;

public class MyPetSittersActivity extends AppCompatActivity implements MyPetSittersView,
        RecyclerViewOnClickListenerHack {

    private MyPetSittersPresenter presenter;
    private OwnerContactListAdapter adapter;

    @Bind(R.id.toolbar_my_pet_sitters)
    Toolbar toolbar;
    @Bind(R.id.rv_owner_contacts_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_sitters);

        ButterKnife.bind(this);

        presenter = new MyPetSittersPresenterImpl(this);

        configureToolbar();
        configureRecyclerView();
    }

    private void configureToolbar() {
        toolbar.setTitle("Meus Pet Sitters");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void configureRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        adapter = new OwnerContactListAdapter(presenter.getContacts(), this);
        adapter.notifyDataSetChanged();
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(MyPetSittersActivity.this, ContactDetailsActivity.class);
        intent.putExtra("contactId", adapter.getContactAtPosition(position).getId());
        startActivity(intent);
    }

    @Override
    public PetSitterApp getPetSitterApp() {
        return (PetSitterApp) getApplication();
    }
}
