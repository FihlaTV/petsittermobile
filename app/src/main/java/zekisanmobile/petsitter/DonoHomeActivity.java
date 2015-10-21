package zekisanmobile.petsitter;

import android.content.Context;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.TabsAdapter;
import zekisanmobile.petsitter.Extras.SlidingTabLayout;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Dono;
import zekisanmobile.petsitter.Model.Sitter;

public class DonoHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;

    private ArrayList<Sitter> sitters;
    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dono_home);

        new JSONResponseHandler().execute(API_SEARCH_URL);

        toolbar = (Toolbar) findViewById(R.id.tb_dono);
        toolbar.setTitle("Ezequiel Guilherme");
        setSupportActionBar(toolbar);

        // TABS
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ColorPrimaryLight));
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mSlidingTabLayout.setViewPager(mViewPager);

        // NAVIGATION DRAWER

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.color.accent_material_light)
                .withTextColor(R.color.primary_text)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Ezequiel Guilherme")
                                .withEmail("zeki-san@hotmail.com")
                                .withIcon(getResources().getDrawable(R.drawable.me))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Toast.makeText(DonoHomeActivity.this, "profilechanged", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        Toast.makeText(DonoHomeActivity.this, "OnItemLongClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })

                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem()
                .withName("Pet Sitters")
                .withIcon(R.drawable.account_star_variant));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem()
                .withName("Pet Sitters 2")
                .withIcon(R.drawable.spotify));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Configuraçoes"));
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

    public ArrayList<Sitter> getSitterList(){
        if (sitters != null) return sitters;
        return null;
    }

    private class JSONResponseHandler extends AsyncTask<String, Void, ArrayList<Sitter>> {

        private final String TAG = JSONResponseHandler.class.getSimpleName();
        private ArrayList<Sitter> returnedSitters = new ArrayList<Sitter>();

        @Override
        protected ArrayList<Sitter> doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONArray jsonArray = new JSONArray(jsonData);
                Context context = getApplicationContext();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int idPhoto = context.getResources().getIdentifier(jsonObject.getString("photo"), "drawable", context.getPackageName());
                    int idBg = context.getResources().getIdentifier(jsonObject.getString("header_background"), "drawable", context.getPackageName());
                    returnedSitters.add(new Sitter(jsonObject.getString("name"),
                            jsonObject.getString("address"),
                            idPhoto,
                            idBg,
                            Float.parseFloat(jsonObject.getString("latitude")),
                            Float.parseFloat(jsonObject.getString("longitude"))));
                }

                return returnedSitters;
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Sitter> receivedSitters) {
            sitters = returnedSitters;
        }
    }
}
