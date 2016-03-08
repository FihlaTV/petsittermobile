package zekisanmobile.petsitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SearchAdapter;
import zekisanmobile.petsitter.Handlers.SearchHandler;
import zekisanmobile.petsitter.Model.SearchItem;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<SearchItem> items = new ArrayList<SearchItem>();
    private List<String> selectedItems = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        items = loadSearchOptions();

        adapter = new SearchAdapter(items);
        recyclerView.setAdapter(adapter);

        Button btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItems.clear();
                for (SearchItem item : items) {
                    if (item.isSelected()) {
                        selectedItems.add(item.getName());
                    }
                }
                try {
                    doSearch();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void doSearch() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (String item: selectedItems) {
            jsonArray.put(item);
        }

        json.put("animals", jsonArray);
        String[] params = {json.toString(), String.valueOf(1)};
        new SearchHandler(getContext()).execute(params);
    }

    private List<SearchItem> loadSearchOptions() {
        if (items.isEmpty()) {
            String[] string_items = getResources().getStringArray(R.array.search_options);
            for (String item : string_items) {
                items.add(new SearchItem(item));
            }
        }
        return items;
    }

}
