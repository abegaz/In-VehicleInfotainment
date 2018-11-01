package bestgroupever.vehicleinfotainmentsystem.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import bestgroupever.vehicleinfotainmentsystem.Model.HVAC_Model;
import bestgroupever.vehicleinfotainmentsystem.R;

public class HVACController extends AppCompatActivity {
    TextView temperature; //temperature display
    Switch acSwitch, heaterSwitch, recycleSwitch; //switches that are used in hvac
    HVAC_Model hvac = new HVAC_Model(); //setting up the hvac model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvaccontroller);
        acSwitch = findViewById(R.id.ac_switch);
        heaterSwitch = findViewById(R.id.heater_switch);
        recycleSwitch = findViewById(R.id.recycle_switch);
        temperature = findViewById(R.id.invehicle_temp);
    }


    public void tempIncrease(View view){ //set hvac model temperature and then use the value to set the text for the temperature text view (increasing)

        hvac.setTemperature(Integer.parseInt(temperature.getText().toString().substring(0,2))+1);
        temperature.setText(""+hvac.getTemperature()+"°F");
    }
    public void tempDecrease(View view){ //set hvac model temperature and then use the value to set the text for the temperature text view (decreasing)
        hvac.setTemperature(Integer.parseInt(temperature.getText().toString().substring(0,2))-1);
        temperature.setText(""+hvac.getTemperature()+"°F");
    }
    public void setAC(View view){ //sets hvac model ac boolean to true or false based of the switch's value
        hvac.setAc(acSwitch.isChecked());
        hvac.setHeater(false);
        heaterSwitch.setChecked(false);
    }
    public void setHeater(View view){ //sets hvac model heater boolean to true or false based of the switch's value
        hvac.setHeater(heaterSwitch.isChecked());
        hvac.setAc(false);
        acSwitch.setChecked(false);
    }
    public void setRecycler(View view) { //sets hvac model recycle_air boolean to true or false based of the switch's value
        hvac.setRecycle_air(recycleSwitch.isChecked());
    }
    public void returnMMenu(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
