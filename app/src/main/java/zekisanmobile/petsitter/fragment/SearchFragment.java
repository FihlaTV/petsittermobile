package zekisanmobile.petsitter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.adapter.SearchAdapter;
import zekisanmobile.petsitter.api.body.SearchSittersBody;
import zekisanmobile.petsitter.handler.SearchHandler;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.view.owner.OwnerHomeActivity;
import zekisanmobile.petsitter.model.SearchItem;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.vo.Owner;

public class SearchFragment extends Fragment {

    @Bind(R.id.rv_search)
    RecyclerView recyclerView;
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
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        items = loadSearchOptions();

        adapter = new SearchAdapter(items);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.btn_search)
    public void onBtnSearchClick() {
        selectedItems.clear();
        for (SearchItem item : items) {
            if (item.isSelected()) {
                selectedItems.add(item.getName());
            }
        }
        doSearch();
    }

    private void doSearch() {
        List<String> animals = new ArrayList<>();
        for (String item : selectedItems) {
            animals.add(item);
        }
        SearchSittersBody searchSittersBody = new SearchSittersBody();
        searchSittersBody.setAnimals(animals);

        Owner owner = new OwnerModel(((PetSitterApp)getActivity().getApplication())
                .getAppComponent()).getLoggedOwnerUser();

        new SearchHandler((OwnerHomeActivity) getActivity(), owner.getApiId(), searchSittersBody)
                .execute();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
