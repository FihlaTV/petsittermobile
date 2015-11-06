package zekisanmobile.petsitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SitterProfileAdapter;
import zekisanmobile.petsitter.Model.Header;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.SitterProfileListItem;

public class SitterProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    Sitter sitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_profile);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("SITTER");


        toolbar = (Toolbar) findViewById(R.id.tb_sitter_profile);
        toolbar.setTitle("Pet Sitter");
        setSupportActionBar(toolbar);

        // NAVIGATION DRAWER

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(getResources().getDrawable(R.drawable.drawer_background))
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
                        Toast.makeText(SitterProfileActivity.this, "profilechanged", Toast.LENGTH_SHORT).show();
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
                        switch (position){
                            case 0:
                                Intent intentHome = new Intent(SitterProfileActivity.this, DonoHomeActivity.class);
                                startActivity(intentHome);
                                break;
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        Toast.makeText(SitterProfileActivity.this, "OnItemLongClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })

                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem()
                .withName("Pet Sitters")
                .withIcon(R.drawable.account));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Configuraçoes"));


        recyclerView = (RecyclerView) findViewById(R.id.sitter_profile_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);

        SitterProfileAdapter adapter = new SitterProfileAdapter(getHeader(), getListItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public Header getHeader() {
        Header header = new Header();
        header.setBackgroundImage(sitter.getProfile_background());
        return header;
    }

    public List<SitterProfileListItem> getListItems() {
        List<SitterProfileListItem> listItems = new ArrayList<SitterProfileListItem>();

        SitterProfileListItem item = new SitterProfileListItem(sitter);
        item.setName("Valores");
        listItems.add(item);

        return listItems;
    }

}
