package zekisanmobile.petsitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SearchAdapter;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.SearchItem;
import zekisanmobile.petsitter.R;

public class SearchFragment extends Fragment implements RecyclerViewOnClickListenerHack{

    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<SearchItem> items = getSearchOptions();

        adapter = new SearchAdapter(items);
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<SearchItem> getSearchOptions() {
        String[] string_items = getResources().getStringArray(R.array.search_options);
        List<SearchItem> items = new ArrayList<SearchItem>();
        for (String item: string_items){
            items.add(new SearchItem(item));
        }
        return items;
    }

    @Override
    public void onClickListener(View view, int position) {

    }
}
