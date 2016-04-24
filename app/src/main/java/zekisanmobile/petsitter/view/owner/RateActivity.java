package zekisanmobile.petsitter.view.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;

public class RateActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_rate)
    Toolbar toolbar;

    @Bind(R.id.iv_like)
    ImageView ivLike;

    @Bind(R.id.iv_dislike)
    ImageView ivDislike;

    @Inject
    ContactModel contactModel;

    private int rate_status = 0; // 0 - not rated, 1 - positive, 2 - negative

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        long contact_id = intent.getLongExtra("contact_id", 0);
        contact = contactModel.find(Realm.getDefaultInstance(), contact_id);

        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @OnClick(R.id.iv_like)
    public void likeContact(){
        changePositiveRate();
    }

    @OnClick(R.id.iv_dislike)
    public void dislikeContact(){
        changeNegativeRate();
    }

    private void changePositiveRate(){
        if (rate_status == 0 || rate_status == 2) {
            ivLike.setImageResource(R.drawable.like_);
            rate_status = 1;
        } else {
            rate_status = 0;
            ivLike.setImageResource(R.drawable.like_gray);
        }
        ivDislike.setImageResource(R.drawable.dislike_gray);
    }

    private void changeNegativeRate(){
        if (rate_status == 0 || rate_status == 1) {
            ivDislike.setImageResource(R.drawable.dislike_);
            rate_status = 2;
        } else {
            rate_status = 0;
            ivDislike.setImageResource(R.drawable.dislike_gray);
        }
        ivLike.setImageResource(R.drawable.like_gray);
    }
}
