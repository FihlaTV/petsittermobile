package zekisanmobile.petsitter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_details);

        String title = getIntent().getStringExtra("title");
        int bitmap = getIntent().getIntExtra("image", 0);

        TextView titleTextView = (TextView) findViewById(R.id.detail_title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        imageView.setImageResource(bitmap);
    }

}
