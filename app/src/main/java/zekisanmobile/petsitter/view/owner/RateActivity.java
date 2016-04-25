package zekisanmobile.petsitter.view.owner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.realm.Realm;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.controller.contact.ContactController;
import zekisanmobile.petsitter.model.ContactModel;
import zekisanmobile.petsitter.vo.Contact;

public class RateActivity extends AppCompatActivity implements RateView {

    //region Members
    @Bind(R.id.toolbar_rate)
    Toolbar toolbar;

    @Bind(R.id.iv_like)
    ImageView ivLike;

    @Bind(R.id.iv_dislike)
    ImageView ivDislike;

    @Bind(R.id.iv_owner)
    ImageView ivOwner;

    @Bind(R.id.iv_send_comment)
    ImageView ivSendComment;

    @Bind(R.id.et_comment)
    EditText etComment;

    @Inject
    ContactModel contactModel;

    @Inject
    ContactController controller;

    private int rate_status = 0; // 0 - not rated, 1 - positive, 2 - negative

    private RatePresenter presenter;

    Contact contact;
    //endregion

    //region Inherited Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        presenter = new RatePresenterImpl(this);
        int imageId = getApplicationContext().getResources().getIdentifier(
                presenter.getLoggedUserPhoto(), "drawable",
                getApplicationContext().getPackageName());
        ivOwner.setImageResource(imageId);

        Intent intent = getIntent();
        long contact_id = intent.getLongExtra("contact_id", 0);
        contact = contactModel.find(Realm.getDefaultInstance(), contact_id);

        configureToolbar();
    }

    @OnClick(R.id.iv_like)
    public void likeContact(){
        changePositiveRate();
    }

    @OnClick(R.id.iv_dislike)
    public void dislikeContact(){
        changeNegativeRate();
    }

    @OnClick(R.id.iv_send_comment)
    public void sendComment() {
        hideKeyboard();
        if (rate_status == 0) {
            showWarningDialog();
        } else {
            controller.rateContact(true, presenter.getLoggedUserId(), contact.getId(),
                    rate_status == 1, etComment.getText().toString());
        }
    }

    @OnTextChanged(R.id.et_comment)
    public void onTextChanged(){
        if (etComment.getText().toString().trim().length() == 0) {
            ivSendComment.setVisibility(View.INVISIBLE);
        } else {
            ivSendComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public PetSitterApp getPetSitterApp() {
        return (PetSitterApp) getApplication();
    }
    //endregion

    //region Methods
    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    private void showWarningDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Por favor, dê um like ou dislike para este Pet Sitter.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
        keepDialog(dialog);
    }

    private void keepDialog(Dialog dialog){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    //endregion
}
