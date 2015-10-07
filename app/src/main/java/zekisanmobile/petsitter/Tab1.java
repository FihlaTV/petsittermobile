package zekisanmobile.petsitter;

import android.content.res.Resources;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezequiel on 28/09/15.
 */
public class Tab1 extends ListFragment {
    private List<ListViewItem> mItems;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        Resources resources = getResources();

        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter1), getString(R.string.aim), getString(R.string.aim_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter2), getString(R.string.bebo), getString(R.string.bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter3), getString(R.string.youtube), getString(R.string.youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter4), getString(R.string.lucia), getString(R.string.lucia_description)));

        // initialize and set the list adapter
        setListAdapter(new ListViewDemoAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //Retrieve the list view item
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }
}
