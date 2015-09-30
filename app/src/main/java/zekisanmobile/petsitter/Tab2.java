package zekisanmobile.petsitter;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
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
import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Model.Sitter;

/**
 * Created by ezequiel on 28/09/15.
 */
public class Tab2 extends Fragment {

    private static final String API_SEARCH_URL = "https://petsitterapi.herokuapp.com/api/v1/sitters";
    private static final String TAG = Tab2.class.getSimpleName();

    // Coordinates used for centering the map
    private static final double CAMERA_LNG = -30.034647;
    private static final double CAMERA_LAT = -51.217658;

    // The Map object
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);
        new JSONResponseHandler().execute(API_SEARCH_URL);
        return v;
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
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null){
                Geocoder gc = new Geocoder(getActivity());
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
