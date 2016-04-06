package zekisanmobile.petsitter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.Util.ContactStatusString;

public class OwnerContactListAdapter extends RecyclerView.Adapter<OwnerContactListAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Context parentContext;

    public OwnerContactListAdapter(ArrayList<Contact> contacts, Context parentContext){
        this.contacts = contacts;
        this.parentContext = parentContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_owner_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnerContactListAdapter.ViewHolder holder, int position) {
        if (contacts != null || contacts.size() == 0) {
            Contact contact = contacts.get(position);

            int imageId = parentContext.getResources().getIdentifier(contact.getSitter().getPhoto(), "drawable", parentContext.getPackageName());
            holder.iv_contact_sitter.setImageResource(imageId);
            holder.tv_contact_sitter.setText(contact.getSitter().getName());
            holder.tv_contact_status.setText(ContactStatusString.getStatusName(contact.getStatus(),
                    contact.getDate_start(), contact.getDate_final()));
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack){
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.iv_contact_sitter)
        ImageView iv_contact_sitter;
        @Bind(R.id.tv_contact_sitter)
        TextView tv_contact_sitter;
        @Bind(R.id.tv_contact_status) TextView tv_contact_status;

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
