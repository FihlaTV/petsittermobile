package zekisanmobile.petsitter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ezequiel on 28/09/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb){
        super(fm);

        this.Titles = mTitles;
        this.numOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            Tab1 tab1 = new Tab1();
            return tab1;
        }
        else
        {
            Tab2 tab2 = new Tab2();
            return tab2;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
