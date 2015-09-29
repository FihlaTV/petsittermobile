package zekisanmobile.petsitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ezequiel on 28/09/15.
 */
public class Tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstance){
        View v = inflater.inflate(R.layout.tab_1, container, false);
        return v;
    }

}
