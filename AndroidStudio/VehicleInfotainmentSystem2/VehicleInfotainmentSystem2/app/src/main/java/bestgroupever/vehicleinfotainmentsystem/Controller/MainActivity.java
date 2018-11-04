package bestgroupever.vehicleinfotainmentsystem.Controller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import bestgroupever.vehicleinfotainmentsystem.R;


public class MainActivity extends AppCompatActivity {

    private ImageView main_map_button;
    BluetoothAdapter bluetoothAdapter;
    Intent intent;

    //Creating a calendar object to pull system times and dates for the main display

    Date currentTime = Calendar.getInstance().getTime();
    TextView time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView bluetoothOnOff = findViewById(R.id.main_menu_settings);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //set time in the main menu
        time = findViewById(R.id.time_view);
        time.setText(currentTime.toString());


        //button enables and disables bluetooth
        bluetoothOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", "onClick: enabling/disabling bluetooth.");
                enableDisableBT();
            }
        });

    }

    //this code adds the little settings menu in the top right

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "onDestroy: called");
        unregisterReceiver(broadcastReceiver);
    }

    //Method to enable bluetooth if not enabled, and conversely
    protected void enableDisableBT() {
        if (bluetoothAdapter == null) {
            Log.d("tag", "enableDisableBT: Does not have bluetooth capabilities.");
        }

        if (!bluetoothAdapter.isEnabled()) {
            Log.d("tag", "enableDisableBT: enabling bluetooth");
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetoothIntent);

            IntentFilter bluetoothIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiver, bluetoothIntent);
        }

        if (bluetoothAdapter.isEnabled()) {
            Log.d("tag", "enableDisableBT: disabling bluetooth");
            bluetoothAdapter.disable();

            IntentFilter bluetoothIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiver, bluetoothIntent);
        }
    }

    //Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                //tracks the state of the Bluetooth status
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d("Tag", "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d("Tag", "BroadcastReceiver: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d("Tag", "BroadcastReceiver: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d("Tag", "BroadcastReceiver: STATE TURNING ON");
                        break;
                }
            }
        }
    };



//this method is used to make the imageViews work. Call this method in the android:onClick in the xml file

    public void changeLayout(View v){

        switch (v.getId()){

            case R.id.main_gps_button:
                setContentView(R.layout.gps_menu);
                break;

            case R.id.main_music_button:
                setContentView(R.layout.music_menu);
                break;

            case R.id.main_hvac_button:
                intent = new Intent(this, HVACController.class);
                startActivity(intent);
                break;

            case R.id.main_weather_button:
                intent = new Intent(this, Weather_Activity.class);
                startActivity(intent);
                break;

            case R.id.back_button:
                setContentView(R.layout.activity_main);




        }


    }}


/*
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //check
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Getting latitude
                    double latitude = location.getLatitude();
                    // Getting longitude
                    double longitude = location.getLongitude();
                    // Instantiate LatLng class
                    LatLng latLng = new LatLng(latitude, longitude);
                    // Instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Getting latitude
                    double latitude = location.getLatitude();
                    // Getting longitude
                    double longitude = location.getLongitude();
                    // Instantiate LatLng class
                    LatLng latLng = new LatLng(latitude, longitude);
                    // Instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }




     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*

    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;

        // Add a marker in Dahlonega and move the camera
        //LatLng dahlonega = new LatLng(34.5279067, -84.0179191);
        //mMap.addMarker(new MarkerOptions().position(dahlonega).title("Marker in Dahlonega"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(dahlonega));
    }


*/