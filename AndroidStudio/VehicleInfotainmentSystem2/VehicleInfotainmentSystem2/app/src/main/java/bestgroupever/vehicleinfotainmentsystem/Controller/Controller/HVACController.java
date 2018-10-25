package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import bestgroupever.vehicleinfotainmentsystem.R;

public class HVACController extends AppCompatActivity {
    TextView temperature = findViewById(R.id.invehicle_temp); //temperature display
    Button tempIncrease = findViewById(R.id.temp_increase), tempDecrease = findViewById(R.id.temp_decrease);
    Switch acSwitch = findViewById(R.id.ac_switch), heaterSwitch = findViewById(R.id.heater_switch), recycleSwitch = findViewById(R.id.recycle_switch); //switches that are used in hvac
    HVAC_Model hvac = new HVAC_Model(); //setting up the hvac model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvaccontroller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
