/*
Author(s): Trevor, Connor
Date: 10/18/18
*/
package Controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import Model.HVAC_Model;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import e.julie.vehicleinfotainmentsystem.R;

public class HVAC_Controller extends AppCompatActivity {
    TextView temperature = findViewById(R.id.invehicle_temp); //temperature display
    //   Button tempIncrease = findViewById(R.id.temp_increase), tempDecrease = findViewById(R.id.temp_decrease);
    Switch acSwitch = findViewById(R.id.ac_switch), heaterSwitch = findViewById(R.id.heater_switch), recycleSwitch = findViewById(R.id.recycle_switch); //switches that are used in hvac
    HVAC_Model hvac = new HVAC_Model(); //setting up the hvac model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    public void tempIncrease(){
        hvac.setTemperature(Integer.parseInt((String) temperature.getText())+1);
        temperature.setText(hvac.getTemperature());
    }
    public void tempDecrease(){
        hvac.setTemperature(Integer.parseInt((String) temperature.getText())-1);
        temperature.setText(hvac.getTemperature());
    }
    public void setAC(){
        hvac.setAc(acSwitch.isChecked());
    }
    public void setHeater(){
        hvac.setHeater(heaterSwitch.isChecked());
    }
    public void setRecycler(){
        hvac.setRecycle_air(recycleSwitch.isChecked());
    }
}
