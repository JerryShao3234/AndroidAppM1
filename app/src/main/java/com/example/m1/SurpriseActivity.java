package com.example.m1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SurpriseActivity extends AppCompatActivity implements LocationListener {

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
        getTemp();
    }

    @SuppressLint("MissingPermission")
    private void getTemp() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationGPS != null) {
            lat = locationGPS.getLatitude();
            lon = locationGPS.getLongitude();
        }

        RequestQueue queue = Volley.newRequestQueue(SurpriseActivity.this);
        String url = String.format(
                "%s/%s,%s?units=%s",
                "https://api.darksky.net/forecast/96e0788aafdf65a6ffe04079e42a1702",
                Float.toString((float) lat),
                Float.toString((float) lon),
                "us"
        );
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject json = (JSONObject) new JSONObject(response);
                    String temp = json.getJSONObject("currently").getString("temperature");
                    String visibility = json.getJSONObject("currently").getString("summary");
                    float tempC = ((float) Float.valueOf(temp) - 32.0F) * (5.0F/9.0F);
                    dispWeatherTxt.setText("Current weather at your location (" + getCity() + ")" + "\n\n" + "Weather outlook: " + visibility + "\n" +
                            "Temperature: " + Float.toString(tempC)+ " C\n\n" + "Disclaimer: this data was gathered using the " +
                            "DarkSky.net forecast weather API, which is an external service");

                } catch (JSONException e) {
                    dispWeatherTxt.setText("API request failed");
                    Log.d("Surprise Activity", "JSON failed");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SurpriseActivity",error.toString());
                dispWeatherTxt.setText("API request failed + exception");
            }
        } );
        queue.add(req);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private String getCity() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        }
        catch (Exception e) {
            Log.d("SurpriseActivity", "getCity() had an exception!");
        }
        return addresses.get(0).getLocality();
    }
}
