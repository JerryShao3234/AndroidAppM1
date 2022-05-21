package com.example.m1;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class ModelCityActivity2 extends MapsActivity {
    TextView textView ;
    TextView textViewManu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_city2);
        textView = findViewById(R.id.textView ) ;
        textView .setText("Phone Model:\n" + Build.MODEL) ;
        textViewManu = findViewById(R.id.manufacturer_id);
        textViewManu.setText("Manufacturer:\n" + Build.MANUFACTURER) ;
        this.displayLocation(getCity());
    }

    public void displayLocation(String location) {
        textView = findViewById(R.id.textView2 ) ;
        textView.setText("Current City:\n" + location) ;
    }

}