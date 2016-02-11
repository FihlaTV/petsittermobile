package zekisanmobile.petsitter.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import zekisanmobile.petsitter.R;

public class SearchItemHolder extends RecyclerView.ViewHolder {

    public TextView tv_search_item;
    public CheckBox chk_search_item;

    public SearchItemHolder(View view) {
        super(view);
        this.tv_search_item = (TextView) view.findViewById(R.id.tv_search_item);
        this.chk_search_item = (CheckBox) view.findViewById(R.id.chk_search_item);
    }

}
