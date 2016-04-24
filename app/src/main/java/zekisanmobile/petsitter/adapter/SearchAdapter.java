package zekisanmobile.petsitter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.model.SearchItem;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemHolder>{

    //region Members
    private List<SearchItem> items;
    //endregion

    //region Constructors
    public SearchAdapter(List<SearchItem> items) {
        this.items = items;
    }
    //endregion

    //region Inherited Methods
    @Override
    public SearchItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, null);
        return new SearchItemHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchItemHolder searchItemHolder, final int position) {
        SearchItem item = items.get(position);

        searchItemHolder.chk_search_item.setText(item.getName());
        searchItemHolder.chk_search_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(position).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }
    //endregion

    //region Inner Class
    public class SearchItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.chk_search_item) CheckBox chk_search_item;

        public SearchItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    //endregion
}