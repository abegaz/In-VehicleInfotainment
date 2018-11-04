package bestgroupever.vehicleinfotainmentsystem.Model;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import bestgroupever.vehicleinfotainmentsystem.Controller.MainActivity;
import bestgroupever.vehicleinfotainmentsystem.R;

public class Weather_Activity extends AppCompatActivity {

    Intent intent;
    static ImageView conditionImageView;
    static TextView lowTempTextView;
    static TextView highTempTextView;
    static TextView humidityTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        conditionImageView = findViewById(R.id.ivCondition);
        lowTempTextView = findViewById(R.id.tvLow);
        highTempTextView = findViewById(R.id.tvHigh);
        humidityTextView = findViewById(R.id.tvHumidity);
        String city = "Dahlonega";
        DownloadJSON task = new DownloadJSON();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=d5bb9d4bb6d89bf2b50b7e2c39e5c8cf");


    }
    //this method is used to make the imageViews work. Call this method in the android:onClick in the xml file

    public void changeLayout(View v){

        switch (v.getId()){


            case R.id.back_button:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);




        }


    }
}

