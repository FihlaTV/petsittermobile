package zekisanmobile.petsitter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Util.Formatter;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Context parentContext;

    public ContactListAdapter(ArrayList<Contact> contacts, Context parentContext){
        this.contacts = contacts;
        this.parentContext = parentContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (contacts != null || contacts.size() == 0) {
            Contact contact = contacts.get(position);

            int imageId = parentContext.getResources().getIdentifier("me", "drawable", parentContext.getPackageName());
            holder.iv_contact_owner.setImageResource(imageId);
            holder.tv_contact_owner.setText(contact.getOwner().getName());
            holder.tv_contact_created_at.setText(Formatter.formattedDateFromString(contact.getCreated_at()));
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack){
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }

    public Contact getContactAtPosition(int position) {
        return contacts.get(position);
    }

    public void updateContactsList(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.iv_contact_owner) ImageView iv_contact_owner;
        @Bind(R.id.tv_contact_owner) TextView tv_contact_owner;
        @Bind(R.id.tv_contact_created_at) TextView tv_contact_created_at;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerViewOnClickListenerHack != null){
                recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
