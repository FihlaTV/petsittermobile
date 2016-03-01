package zekisanmobile.petsitter;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Model.User;

public class SitterHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_home);

        user = UserDAO.getLoggedUser(1);

        configureToolbar();
    }

    private void configureToolbar() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(user.getSitter().getName());

        ImageView image_sitter_profile = (ImageView) findViewById(R.id.image_sitter_home);
        int imageId = getResources().getIdentifier("header_background_2", "drawable", getPackageName());
        image_sitter_profile.setImageResource(imageId);

        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar_sitter_home);
        setSupportActionBar(toolbar);
    }
}
