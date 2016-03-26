package zekisanmobile.petsitter.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Adapters.ContactListAdapter;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Sitter.ContactDetailsActivity;

public class ContactsByStatusFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    @Bind(R.id.rv_list_received_contacts)
    RecyclerView rvListReceivedContacts;

    private ArrayList<Contact> contacts;
    private ContactListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_contacts_by_status, container, false);
        ButterKnife.bind(this, view);
        contacts = new ArrayList<>();

        configureAdapter();
        configureRecyclerView();

        return view;
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvListReceivedContacts.setLayoutManager(layoutManager);
        rvListReceivedContacts.setAdapter(adapter);
    }

    private void configureAdapter() {
        adapter = new ContactListAdapter(contacts, getContext());
        adapter.setRecyclerViewOnClickListenerHack(this);
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
        intent.putExtra("contactId", adapter.getContactAtPosition(position).getId());
        startActivity(intent);
    }

    public void updateContactsList(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        if (adapter == null) {
            adapter = new ContactListAdapter(contacts, getContext());
        }
        adapter.updateContactsList(contacts);
        adapter.notifyDataSetChanged();
    }
}
