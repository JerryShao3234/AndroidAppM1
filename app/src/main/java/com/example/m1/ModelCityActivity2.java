package com.example.m1;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModelCityActivity2 extends Activity {
    TextView textView ;
    TextView textViewManu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_city2);
        textView = findViewById(R.id.textView ) ;
        textView.setText(Html.fromHtml("<b>" + "Phone Model:\n" + "</b>" + Build.MODEL)) ;
        textViewManu = findViewById(R.id.manufacturer_id);
        textViewManu.setText(Html.fromHtml("<b>" + "Manufacturer:\n" + "</b>" + Build.MANUFACTURER)) ;
        this.displayLocation(getCity());
    }

    public String getCity() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS == null) {
                    return null;
                } else {
                    double lat = 0.0;
                    double lon = 0.0;
                    if (locationGPS != null) {
                        lat = locationGPS.getLatitude();
                        lon = locationGPS.getLongitude();
                    }

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(lat, lon, 1);
                    } catch (Exception e) {
                        Log.d("MCA2", "getCity() had an exception!");
                    }
                    return addresses.get(0).getLocality();
                }
            }
        }
        catch (Error e) {
            return "Vancouver";
        }
        return "Vancouver";
    }

    public void displayLocation(String location) {
        textView = findViewById(R.id.textView2 ) ;
        textView.setText(Html.fromHtml("<b>" + "Current City:\n" + "</b>" + location)) ;
    }

}