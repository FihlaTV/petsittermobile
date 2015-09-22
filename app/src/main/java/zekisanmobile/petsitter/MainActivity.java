package zekisanmobile.petsitter;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Sitter;

public class MainActivity extends AppCompatActivity {

    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView myText;

    // Coordinates used for centering the map
    private static final double CAMERA_LNG = -30.034647;
    private static final double CAMERA_LAT = -51.217658;

    // The Map object
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JSONResponseHandler().execute(API_SEARCH_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    returnedSitters.add(new Sitter(jsonObject.getString("name"), jsonObject.getString("address")));
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
        protected void onPostExecute(ArrayList<Sitter> receivedSitters){

            // Get the Map Object
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null){
                Geocoder gc = new Geocoder(MainActivity.this);
                try{
                    for(int i = 0; i < receivedSitters.size(); i++) {
                        List<Address> addresses = gc.getFromLocationName(receivedSitters.get(i).getAddress(), 1);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()))
                                .title(receivedSitters.get(i).getName())
                                .icon(BitmapDescriptorFactory.defaultMarker((float) (120 * (8.5 - 6))))
                                .snippet(receivedSitters.get(i).getAddress()));
                    }
                }catch (IOException e){
                    Log.d(TAG, e.getMessage());
                }
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
                    CAMERA_LAT, CAMERA_LNG)));
        }
    }
}
