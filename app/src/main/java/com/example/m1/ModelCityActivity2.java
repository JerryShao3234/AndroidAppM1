package com.example.m1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle ;
import android.util.Log;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModelCityActivity2 extends MapsActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_city2);
        textView = findViewById(R.id.textView ) ;
        textView .setText("Phone Model:\n" + Build.MODEL + Build.BOARD + Build.BRAND) ;
        this.displayLocation(getCity());
    }

    public void displayLocation(String location) {
        textView = findViewById(R.id.textView2 ) ;
        textView.setText("Current City:\n" + location) ;
    }

}