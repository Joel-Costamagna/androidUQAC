package com.example.projet.projetandroid.boussole;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.projet.projetandroid.R;


public class BoussoleActivity extends Activity {

    private static final String TAG = "BoussoleActivity";

    private Boussole compass;

    private MyGPSLocation gps;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boussole);

        compass = new Boussole(this);
        compass.dialView = findViewById(R.id.main_image_dial);
        compass.textview_azimuth = findViewById(R.id.azimuth);

        gps = new MyGPSLocation(this);
        gps.longitude = findViewById(R.id.longitude);
        gps.latitude = findViewById(R.id.latitude);
        gps.altitude = findViewById(R.id.altitude);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        compass.start();
        gps.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
        gps.stop();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        compass.start();
        gps.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compass.stop();
        gps.stop();
    }

}