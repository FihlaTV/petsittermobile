package zekisanmobile.petsitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.TabsAdapter;
import zekisanmobile.petsitter.Extras.SlidingTabLayout;
import zekisanmobile.petsitter.Model.Sitter;

public class DonoHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dono_home);

        toolbar = (Toolbar) findViewById(R.id.tb_dono);
        toolbar.setTitle("Ezequiel Guilherme");
        toolbar.setLogo(R.drawable.me_small);
        setSupportActionBar(toolbar);

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        // Assiging the Sliding Tab Layout View
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ColorAccent));
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabLayout.setViewPager(mViewPager);
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
