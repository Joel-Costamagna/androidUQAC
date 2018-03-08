package com.example.projet.projetandroid.Boussole;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.projet.projetandroid.R;


public class BoussoleActivity extends Activity {

    private static final String TAG = "BoussoleActivity";

    private Boussole compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boussole);

        compass = new Boussole(this);
        compass.arrowView = findViewById(R.id.main_image_hands);
        compass.textview_azimuth = findViewById(R.id.azimuth);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }

}
