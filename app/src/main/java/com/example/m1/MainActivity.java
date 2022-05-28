package com.example.m1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private Button mapsButton;
    private Button favCityButton;
    private Button surpriseButton;
    private Button serverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermissions();
        setContentView(R.layout.activity_main);

        mapsButton = findViewById(R.id.phone_model);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermissions();
                Log.d(TAG, "Trying to open Google Maps");
                Intent displayIntent = new Intent(MainActivity.this, ModelCityActivity2.class);
                startActivity(displayIntent);
            }
        });

        favCityButton = findViewById(R.id.my_fav_city);
        favCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Selecting favourite city");
                Intent favCityIntent = new Intent(MainActivity.this, Maps1.class);
                startActivity(favCityIntent);
            }
        });

        surpriseButton = findViewById(R.id.surprise_button);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Surprise deploying!");
                Intent surpriseIntent = new Intent(MainActivity.this, SurpriseActivity.class);
                startActivity(surpriseIntent);
            }
        });

        serverButton = findViewById(R.id.server_button);
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Server message showing!");
                Intent serverIntent = new Intent(MainActivity.this, Server.class);
                startActivity(serverIntent);
            }
        });

    }



    private void checkLocationPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE)) {
                Toast.makeText(MainActivity.this, "We need these permissions to run!", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need Location Permissions")
                        .setMessage("We need the location permissions to mark your location on a map")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE}, 1);
                            }
                        })
                        .create()
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE}, 1);
            }
        }
    }

}