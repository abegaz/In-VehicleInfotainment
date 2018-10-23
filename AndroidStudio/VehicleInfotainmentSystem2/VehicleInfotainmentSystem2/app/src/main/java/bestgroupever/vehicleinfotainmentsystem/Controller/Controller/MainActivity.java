package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

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

import bestgroupever.vehicleinfotainmentsystem.R;


public class MainActivity extends AppCompatActivity {

    private ImageView main_map_button;
    BluetoothAdapter bluetoothAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bluetoothOnOff = findViewById(R.id.onOffBluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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
        Log.d("Tag", "onDestroy: called");
        unregisterReceiver(broadcastReceiver);
    }

    //Method to enable bluetooth if not enabled, and conversely
    protected void enableDisableBT() {
        if (bluetoothAdapter == null) {
            Log.d("Tag", "enableDisableBT: Does not have bluetooth capabilities.");
        }

        if (!bluetoothAdapter.isEnabled()) {
            Log.d("Tag", "enableDisableBT: enabling bluetooth");
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetoothIntent);

            IntentFilter bluetoothIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiver, bluetoothIntent);
        }

        if (bluetoothAdapter.isEnabled()) {
            Log.d("Tag", "enableDisableBT: disabling bluetooth");
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
                setContentView(R.layout.hvac_menu);
                break;

            case R.id.back_button:
                setContentView(R.layout.activity_main);

        }


    }
}