package com.example.m1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONException;
import org.json.JSONObject;


public class SurpriseActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    TextView dispWeatherTxt;
    private double lat = 0.0;
    private double lon = 0.0;
    String api_id = "6d0b646579fd4018819b70de51109d91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);
        dispWeatherTxt =  findViewById(R.id.weather_id);
        dispWeatherTxt.setText("asdasd");
        getTemp();
    }

    @SuppressLint("MissingPermission")
    private void getTemp() {
        /*RequestQueue queue = Volley.newRequestQueue(this);
        final String[] weather_url1 = {""};
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location ->
        {
            if(location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                dispWeatherTxt.setText(String.format(Locale.US, "%s -- %s", lat, lon));
                weather_url1[0] = "https://api.weatherbit.io/v2.0/current?" + lat + "&lon=" + lon + "&key=" + api_id;
            }
        }); */
        RequestQueue queue = Volley.newRequestQueue(SurpriseActivity.this);
        String awu = "https://api.darksky.net/forecast/96e0788aafdf65a6ffe04079e42a1702"; // "https://api.weatherbit.io/v2.0/current?" + "11" + "&lon=" + "12" + "&key" + api_id;
        String url = String.format(
                "%s/%s,%s?units=%s",
                "https://api.darksky.net/forecast/96e0788aafdf65a6ffe04079e42a1702",
                Float.toString(11.0F),
                Float.toString(14.0F),
                "us"
        );
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject json = (JSONObject) new JSONObject(response);
                } catch (JSONException e) {
                    Log.d("Surprise Activity", "JSON failed");
                }
                dispWeatherTxt.setText(response);

               // dispWeatherTxt.setText("The weather is currently " + (String) json.get(""));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dispWeatherTxt.setText("asdasd3");
                Log.d("SurpriseActivity",error.toString());
            }
        } );
        queue.add(req);
    }
}
