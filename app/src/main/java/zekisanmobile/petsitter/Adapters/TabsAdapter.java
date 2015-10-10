package zekisanmobile.petsitter.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zekisanmobile.petsitter.Fragments.MapsFragment;
import zekisanmobile.petsitter.Fragments.SitterFragment;

/**
 * Created by ezequiel on 10/10/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    Context mContext;
    private String[] Titles = {"LISTA", "MAPA"};

    public TabsAdapter(FragmentManager fm, Context context){
        super(fm);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        switch (position){
            case 0:
                frag = new SitterFragment();
                break;
            case 1:
                frag = new MapsFragment();
                break;
        }

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return Titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return Titles[position];
    }
}
