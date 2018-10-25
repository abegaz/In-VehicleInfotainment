package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import bestgroupever.vehicleinfotainmentsystem.R;

public class HVACController extends AppCompatActivity {
    HVAC_Model hvac = new HVAC_Model(); //setting up the hvac model
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvaccontroller);
    }
}
