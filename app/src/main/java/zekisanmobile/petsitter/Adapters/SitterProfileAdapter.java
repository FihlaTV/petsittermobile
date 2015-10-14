package zekisanmobile.petsitter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zekisanmobile.petsitter.Model.Header;
import zekisanmobile.petsitter.Model.SitterProfileListItem;
import zekisanmobile.petsitter.R;

/**
 * Created by ezequiel on 11/10/15.
 */
public class SitterProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Header header;
    List<SitterProfileListItem> listItems;

    public SitterProfileAdapter(Header header, List<SitterProfileListItem> listItems){
        this.header = header;
        this.listItems = listItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sitter_header, parent, false);
            return new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sitter_list_item, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    private SitterProfileListItem getItem(int position){
        return listItems.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader){
            VHHeader VHheader = (VHHeader) holder;
            VHheader.txtTitle.setText(header.getHeaderText());
            VHheader.txtTitle.setBackgroundResource(header.getBackgroundImage());
        }
        else if(holder instanceof VHItem){
            SitterProfileListItem currentItem = getItem(position - 1);
            VHItem VHitem = (VHItem) holder;
            VHitem.txtName.setText(currentItem.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listItems.size()+1;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView txtName;
        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (TextView)itemView.findViewById(R.id.txtName);
        }
    }
}
