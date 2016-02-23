package zekisanmobile.petsitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import zekisanmobile.petsitter.Model.Sitter;

public class NewContactActivity extends AppCompatActivity {

    private Sitter sitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("sitter");

        configureViews();
    }

    private void configureViews() {
        ImageView iv_sitter = (ImageView) findViewById(R.id.iv_sitter);
        iv_sitter.setImageResource(sitter.getPhoto());

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(sitter.getName());
    }
}
