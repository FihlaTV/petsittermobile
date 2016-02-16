package zekisanmobile.petsitter.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SitterAdapter;
import zekisanmobile.petsitter.OwnerHomeActivity;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.SitterProfileActivity;

public class SitterFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private SitterAdapter adapter;
    private ArrayList<Sitter> sitters;

    public SitterFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            sitters = (ArrayList<Sitter>)savedInstanceState.getSerializable("sittersList");
        }else {
            sitters = ((OwnerHomeActivity) getActivity()).getSitterList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_sitter, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        adapter = new SitterAdapter(sitters);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putSerializable("sittersList", sitters);
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), SitterProfileActivity.class);
        intent.putExtra("SITTER", sitters.get(position));
        startActivity(intent);
    }

    public void updateSittersList(ArrayList<Sitter> mList){
        this.sitters = mList;
        adapter.updateSittersList(mList);
    }
}
