package zekisanmobile.petsitter.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.SitterProfileActivity;
import zekisanmobile.petsitter.Util.SitterMarker;

public class MapsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowCloseListener{

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 60000; // 60 seconds
    private long FASTEST_INTERVAL = 5000; // 5 seconds
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private static final String TAG = MapsFragment.class.getSimpleName();
    private static View rootView;
    private List<Sitter> sitters;

    private HashMap<Marker, SitterMarker> markerSitterMarkerHashMap;

    public MapsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null)
                    parent.removeView(rootView);
            }

            try{
                if(rootView == null)
                {
                    rootView = inflater.inflate(R.layout.fragment_maps, container, false);
                }
                mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
                if (mapFragment != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            loadMap(googleMap);
                        }
                    });
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    protected void loadMap(GoogleMap googleMap){
        map = googleMap;
        if(map != null){
            try {
                map.setOnInfoWindowClickListener(this);
                map.setMyLocationEnabled(true);

                googleApiClient = new GoogleApiClient.Builder(getActivity())
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).build();
                connectClient();

            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

    protected void connectClient(){
        if(isGooglePlayServicesAvailable() && googleApiClient != null){
            googleApiClient.connect();
        }
    }

    public void setSitters(List<Sitter> sitters) {
        this.sitters = sitters;
    }

    public void clearAllMarkers(){
        if(map != null) map.clear();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectClient();
    }

    @Override
    public void onStop() {
        if( googleApiClient != null && googleApiClient.isConnected() ) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {

        if (mapFragment.isResumed()){
            getFragmentManager().beginTransaction().remove(mapFragment).commit();
        }

        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        googleApiClient.connect();
                        break;
                }
        }
    }

    private boolean isGooglePlayServicesAvailable(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if(ConnectionResult.SUCCESS == resultCode){
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        }

        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                startLocationUpdates();
                updateMapMarkers();
                map.animateCamera(cameraUpdate);
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    protected void startLocationUpdates() {
        try{
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    public void updateMapMarkers() {
        if (map != null){
            try{
                markerSitterMarkerHashMap = new HashMap<>();
                for(int i = 0; i < sitters.size(); i++) {
                    map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(sitters.get(i).getLatitude(), sitters.get(i).getLongitude()))
                            .title(sitters.get(i).getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pet_marker))
                            .snippet(sitters.get(i).getAddress()));
                    int imageId = getResources().getIdentifier(sitters.get(i).getPhoto(), "drawable", getActivity().getPackageName());
                    markerSitterMarkerHashMap.put(marker, new SitterMarker(sitters.get(i).getName(), sitters.get(i).getAddress(), imageId, sitters.get(i)));
                }

            }catch (Exception e){
                Log.d(TAG, e.getMessage());
            }

            //if (currentLocation != null) initCamera(currentLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(), SitterProfileActivity.class);
        intent.putExtra("SITTER", markerSitterMarkerHashMap.get(marker).getSitter());
        startActivity(intent);
    }

    @Override
    public void onInfoWindowClose(Marker marker) {

    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        CustomInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.content_marker, null);
            SitterMarker sitterMarker = markerSitterMarkerHashMap.get(marker);
            ImageView badge = (ImageView) view.findViewById(R.id.badge);
            badge.setImageResource(sitterMarker.getBadge());

            String title = sitterMarker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if(title != null){
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            }else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if(snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(),0);
                snippetUi.setText(snippetText);
            }else{
                snippetUi.setText("");
            }

            return view;
        }
    }
}