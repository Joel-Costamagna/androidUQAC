package com.example.projet.projetandroid.boussole;

import android.app.Activity;
import android.os.Bundle;

import com.example.projet.projetandroid.R;


public class BoussoleActivity extends Activity {

    private static final String TAG = "BoussoleActivity";

    private Boussole compass;

    private MyGPSLocation gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boussole);


        compass = new Boussole(this);
        compass.arrowView = findViewById(R.id.main_image_dial);
        compass.textview_azimuth = findViewById(R.id.azimuth);

        gps = new MyGPSLocation(this);

        gps.longitude = findViewById(R.id.longitude);
        gps.latitude = findViewById(R.id.latitude);
        gps.altitude = findViewById(R.id.altitude);




    }

    @Override
    protected void onStart() {
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
