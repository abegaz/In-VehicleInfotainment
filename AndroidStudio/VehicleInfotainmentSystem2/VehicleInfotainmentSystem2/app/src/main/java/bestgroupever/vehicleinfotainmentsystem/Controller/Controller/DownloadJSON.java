package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DownloadJSON extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String read = null;
            StringBuilder output = new StringBuilder();
            while((read = reader.readLine())!=null){
                output.append(read);
            }
            return output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return  null; }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Date date = new Date();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonData = new JSONObject(jsonObject.getString("main"));
            String weatherInfo = jsonObject.getString("weather");
            JSONArray jsonArray = new JSONArray(weatherInfo);
            String description, icon, iconURI;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonData1 = jsonArray.getJSONObject(i);
                description = jsonData1.getString("description");
                icon = jsonData1.getString("icon");
                iconURI = "http://openweathermap.org/img/w/" + icon + ".png";
                Picasso.get().load(iconURI).into(Weather_Activity.conditionImageView);


            }
            double temperatureMin = Double.parseDouble(jsonData.getString("temp_min"));
            double temperatureMax = Double.parseDouble(jsonData.getString("temp_max"));
            double humidity = Double.parseDouble(jsonData.getString("humidity"));
            int temperatureMinFarh = (int)(9/5 * (temperatureMin - 273)) + 32;
            int temperatureMaxFarh= (int)(9/5 * (temperatureMax - 273)) + 32;
            Weather_Activity.lowTempTextView.setText(String.valueOf("Low Temp: "+ temperatureMinFarh));
            Weather_Activity.highTempTextView.setText(String.valueOf(" High Temp: " + temperatureMaxFarh));
            Weather_Activity.humidityTextView.setText(String.valueOf(" Humidity: "+ humidity));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}