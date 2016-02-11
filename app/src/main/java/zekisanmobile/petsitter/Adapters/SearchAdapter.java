package zekisanmobile.petsitter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zekisanmobile.petsitter.Holders.SearchItemHolder;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.SearchItem;
import zekisanmobile.petsitter.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchItemHolder>{

    private List<SearchItem> items;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public SearchAdapter(List<SearchItem> items) {
        this.items = items;
    }

    @Override
    public SearchItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, null);
        return new SearchItemHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchItemHolder searchItemHolder, int i) {
        SearchItem item = items.get(i);

        searchItemHolder.tv_search_item.setText(item.getName());
        searchItemHolder.chk_search_item.setSelected(item.isSelected());
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack) {
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }
}
