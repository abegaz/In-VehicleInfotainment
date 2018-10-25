/*
Author(s): Trevor, Connor
Date: 10/18/18
*/
package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import bestgroupever.vehicleinfotainmentsystem.Controller.Controller.HVAC_Model;
import bestgroupever.vehicleinfotainmentsystem.R;

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

    public void tempIncrease(){ //set hvac model temperature and then use the value to set the text for the temperature text view (increasing)
        hvac.setTemperature(Integer.parseInt((String) temperature.getText())+1);
        temperature.setText(hvac.getTemperature());
    }
    public void tempDecrease(){ //set hvac model temperature and then use the value to set the text for the temperature text view (decreasing)
        hvac.setTemperature(Integer.parseInt((String) temperature.getText())-1);
        temperature.setText(hvac.getTemperature());
    }
    public void setAC(){ //sets hvac model ac boolean to true or false based of the switch's value
        hvac.setAc(acSwitch.isChecked());
        hvac.setHeater(false);
        heaterSwitch.setChecked(false);
    }
    public void setHeater(){ //sets hvac model heater boolean to true or false based of the switch's value
        hvac.setHeater(heaterSwitch.isChecked());
        hvac.setAc(false);
        acSwitch.setChecked(false);
    }
    public void setRecycler(){ //sets hvac model recycle_air boolean to true or false based of the switch's value
        hvac.setRecycle_air(recycleSwitch.isChecked());
    }
}
