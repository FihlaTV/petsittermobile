package zekisanmobile.petsitter.Sitter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import zekisanmobile.petsitter.R;

public class ContactDetailsActivity extends AppCompatActivity implements ContactDetailsView{

    private ContactDetailsPresenter presenter;

    @Bind(R.id.toolbar_contact_details) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        ButterKnife.bind(this);

        this.presenter = new ContactDetailsPresenterImpl(this);

        configureToolbar();
    }

    private void configureToolbar() {
        toolbar.setTitle("Solicitação de Pet Sitter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
