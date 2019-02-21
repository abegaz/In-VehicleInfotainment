package bestgroupever.vehicleinfotainmentsystem.Controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import bestgroupever.vehicleinfotainmentsystem.Model.Maps_Model;
import bestgroupever.vehicleinfotainmentsystem.Model.TaskLoadedCallback;
import bestgroupever.vehicleinfotainmentsystem.R;
import bestgroupever.vehicleinfotainmentsystem.Model.FetchURL;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, TaskLoadedCallback {
    Maps_Model maps_model = new Maps_Model();
    private static final int REQUEST_LOCATION_CODE = 99; //has to be in the class in order to work


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }
        // gets the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (maps_model.getClient() == null) {
                            buildGoogleApiClient();
                        }
                        maps_model.getmMap().setMyLocationEnabled(true);
                    }
                } else // permission is denied
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    /*
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps_model.setmMap(googleMap);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            maps_model.getmMap().setMyLocationEnabled(true);
        }

    }

    // Creating method for onClick button
    public void onClick(View v)
    {
        if(v.getId() == R.id.B_search)
        {
            EditText tf_location = (EditText)findViewById(R.id.TF_location);
            String location = tf_location.getText().toString();
            List<Address> addressList = null;
            maps_model.setDestination(new MarkerOptions());


            if( !location.equals("")){
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i = 0;i<addressList.size() ; i++)
                {
                    Address myAddress = addressList.get(i);
                    LatLng latlng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());

                    maps_model.getDestination().position(latlng);
                    maps_model.getDestination().title("Your search result");

                    //getting direction route set up
                    String url = maps_model.getURL(maps_model.getCurrentLocationMarker().getPosition(), maps_model.getDestination().getPosition(), "driving");
                    new FetchURL(MapsActivity.this).execute(url, "driving");

                    //adding marker to map
                    maps_model.getmMap().addMarker(maps_model.getDestination());
                    maps_model.getmMap().animateCamera(CameraUpdateFactory.newLatLng(latlng));
                }
            }

        }
    }

    protected synchronized void buildGoogleApiClient() {
        maps_model.setClient(new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build());

        maps_model.getClient().connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        maps_model.setLastLocation(location);

        if (maps_model.getCurrentLocationMarker() != null) {
            maps_model.getCurrentLocationMarker().remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        maps_model.setCurrentLocationMarker(maps_model.getmMap().addMarker(markerOptions));

        maps_model.getmMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
       // mMap.animateCamera(CameraUpdateFactory.zoomTo(150));
        maps_model.getmMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18), 5000, null);

        if(maps_model.getClient() != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(maps_model.getClient(), this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        maps_model.setLocationRequest(new LocationRequest());

        maps_model.getLocationRequest().setInterval(1000);
        maps_model.getLocationRequest().setFastestInterval(1000);
        maps_model.getLocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(maps_model.getClient(), maps_model.getLocationRequest(), this);
    }
    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }else{return true;}
    }





    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onTaskDone(Object... values){
        if(maps_model.getCurrentPolyLine() != null){
            maps_model.getCurrentPolyLine().remove();
        }
        maps_model.setCurrentPolyLine(maps_model.getmMap().addPolyline((PolylineOptions) values[0]));
    }

}



