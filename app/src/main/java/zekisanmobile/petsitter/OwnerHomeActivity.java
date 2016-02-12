package zekisanmobile.petsitter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Fragments.MapsFragment;
import zekisanmobile.petsitter.Fragments.SearchFragment;
import zekisanmobile.petsitter.Fragments.SitterFragment;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;

public class OwnerHomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private SitterFragment sitterFragment;
    private TabLayout tabLayout;

    private ArrayList<Sitter> sitters;
    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";

    private Realm realm;
    private RealmResults<User> users;

    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        loggedUser = getLoggedUser();

        new JSONResponseHandler().execute(API_SEARCH_URL);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(loggedUser.getName());
        setSupportActionBar(toolbar);

        // TABS
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // NAVIGATION DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        Context context = getApplicationContext();
        ImageView ivUserImage = (ImageView) header.findViewById(R.id.ivUserImage);
        ivUserImage.setImageResource(context.getResources()
                .getIdentifier(loggedUser.getPhoto(), "drawable", context.getPackageName()));

        TextView tvUsername = (TextView) header.findViewById(R.id.tvUsername);
        tvUsername.setText(loggedUser.getName());

        TextView tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(loggedUser.getEmail());

    }

    private User getLoggedUser() {
        realm = Realm.getDefaultInstance();
        Log.d(OwnerHomeActivity.class.getSimpleName(), "Versão: " + realm.getVersion());
        RealmResults<User> allUsers = realm.where(User.class).findAll();
        User user;
        if (allUsers.size() > 0) {
            user = realm.where(User.class).equalTo("logged", true).findAll().get(0);
        }
        else{
            realm.beginTransaction();
            user = realm.createObject(User.class);
            user.setId(0);
            user.setName("Ezequiel Guilherme");
            user.setEmail("zeki-san@hotmail.com");
            user.setPhoto("me");
            user.setLogged(true);
            user.setType(0);
            realm.commitTransaction();
        }
        return user;
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        sitterFragment = new SitterFragment();
        adapter.addFragment(new SearchFragment(), "PESQUISA");
        adapter.addFragment(sitterFragment, "PET SITTERS");
        adapter.addFragment(new MapsFragment(), "MAPA");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owner_home, menu);
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
        return new ArrayList<Sitter>();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
                            Float.parseFloat(jsonObject.getString("longitude")),
                            jsonObject.getString("district"),
                            Double.valueOf(jsonObject.getString("value_hour")),
                            Double.valueOf(jsonObject.getString("value_shift")),
                            Double.valueOf(jsonObject.getString("value_day")),
                            jsonObject.getString("about_me")));

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
            sitterFragment.setList(sitters);
        }
    }
}
