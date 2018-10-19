package e.julie.vehicleinfotainmentsystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import static e.julie.vehicleinfotainmentsystem.R.layout.activity_main;
import static e.julie.vehicleinfotainmentsystem.R.layout.music_menu;



public class MainActivity extends AppCompatActivity {

private ImageView main_map_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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

        }


}}
