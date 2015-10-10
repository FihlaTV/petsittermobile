package zekisanmobile.petsitter;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Sitter;

public class DonoHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Lista", "Mapa"};
    private int numOfTabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dono_home);

        toolbar = (Toolbar) findViewById(R.id.tb_dono);
        toolbar.setTitle("Ezequiel Guilherme");
        toolbar.setLogo(R.drawable.me_small);
        setSupportActionBar(toolbar);

        /*SitterFragment frag = (SitterFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if (frag == null){
            frag = new SitterFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag, "mainFrag");
            ft.commit();
        }*/

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dono_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Sitter> getSitterList(){
        /*mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter1), getString(R.string.aim), getString(R.string.aim_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter2), getString(R.string.bebo), getString(R.string.bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter3), getString(R.string.youtube), getString(R.string.youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.sitter4), getString(R.string.lucia), getString(R.string.lucia_description)));
        */
        String[] names = new String[]{getString(R.string.aim), getString(R.string.bebo), getString(R.string.youtube), getString(R.string.lucia)};
        int[] photos = new int[]{R.drawable.sitter1, R.drawable.sitter2, R.drawable.sitter3, R.drawable.sitter4};
        List<Sitter> listAux = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Sitter s = new Sitter(names[i % names.length], "", photos[i % photos.length]);
            listAux.add(s);
        }

        return listAux;
    }
}
