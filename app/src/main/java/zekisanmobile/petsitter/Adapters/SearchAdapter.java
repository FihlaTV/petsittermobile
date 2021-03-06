package zekisanmobile.petsitter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.Model.SearchItem;
import zekisanmobile.petsitter.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemHolder>{

    private List<SearchItem> items;

    public SearchAdapter(List<SearchItem> items) {
        this.items = items;
    }

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

    public class SearchItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.chk_search_item) CheckBox chk_search_item;

        public SearchItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}