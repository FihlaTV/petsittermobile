package zekisanmobile.petsitter.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SitterAdapter;
import zekisanmobile.petsitter.DonoHomeActivity;
import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.SitterProfileActivity;

public class SitterFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Sitter> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_sitter, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                SitterAdapter adapter = (SitterAdapter) mRecyclerView.getAdapter();

                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Sitter> listAux = ((DonoHomeActivity) getActivity()).getSitterList();
                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        if (mList != null) {
            mList = ((DonoHomeActivity) getActivity()).getSitterList();
        } else {
            mList = new ArrayList<Sitter>();
        }
        SitterAdapter adapter = new SitterAdapter(getActivity(), mList);
        adapter.setmRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


        new ReloadSitterList().execute("");

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        //Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();

        //SitterAdapter adapter = (SitterAdapter) mRecyclerView.getAdapter();
        //adapter.removeListItem(position);

        Intent intent = new Intent(getActivity(), SitterProfileActivity.class);
        intent.putExtra("SITTER", mList.get(position));
        startActivity(intent);
    }

    private class ReloadSitterList extends AsyncTask<String, Void, ArrayList<Sitter>> {

        @Override
        protected ArrayList<Sitter> doInBackground(String... url) {

            ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();

            return returnedSitters;
        }

        @Override
        protected void onPostExecute(ArrayList<Sitter> receivedSitters) {
            boolean fim = true;

            while (fim) {
                receivedSitters = ((DonoHomeActivity) getActivity()).getSitterList();
                if (receivedSitters != null) {
                    fim = false;
                }
            }
            SitterAdapter adapter = (SitterAdapter) mRecyclerView.getAdapter();
            mList = receivedSitters;
            adapter.setList(receivedSitters);
            adapter.notifyDataSetChanged();
        }
    }
}
