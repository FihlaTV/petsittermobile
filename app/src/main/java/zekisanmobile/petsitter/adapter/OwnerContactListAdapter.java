package zekisanmobile.petsitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.util.ContactStatusString;
import zekisanmobile.petsitter.vo.Contact;

public class OwnerContactListAdapter extends RecyclerView.Adapter<OwnerContactListAdapter.ViewHolder> {

    //region Members
    private List<Contact> contacts;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Context parentContext;
    //endregion

    //region Constructors
    public OwnerContactListAdapter(List<Contact> contacts, Context parentContext){
        this.contacts = contacts;
        this.parentContext = parentContext;
    }
    //endregion

    //region Inherited Methods
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

            int imageId = parentContext.getResources().getIdentifier(contact.getSitter().getPhoto(),
                    "drawable", parentContext.getPackageName());
            holder.iv_contact_sitter.setImageResource(imageId);
            holder.tv_contact_sitter.setText(contact.getSitter().getName());
            holder.tv_contact_status.setText(ContactStatusString.getStatusName(contact.getStatus(),
                    contact.getDateStart(), contact.getDateFinal()));
            holder.tv_contact_period.setText(ContactStatusString.contatPeriod(contact.getDateStart(),
                    contact.getDateFinal()));
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    //endregion

    //region Methods
    public Contact getContactAtPosition(int position){
        return contacts.get(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack){
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }
    //endregion

    //region Inner Class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.iv_contact_sitter)
        ImageView iv_contact_sitter;
        @Bind(R.id.tv_contact_sitter)
        TextView tv_contact_sitter;
        @Bind(R.id.tv_contact_status)
        TextView tv_contact_status;
        @Bind(R.id.tv_contact_period)
        TextView tv_contact_period;

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
    //endregion
}
