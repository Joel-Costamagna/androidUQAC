package com.example.projet.projetandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.projet.projetandroid.boussole.BoussoleActivity;
import com.example.projet.projetandroid.carte.CarteActivity;
import com.example.projet.projetandroid.meteo.MeteoActivity;

public class MainActivity extends Activity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String TAG = "mainActivity";
    boolean useDarkTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        useDarkTheme = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean(PREF_DARK_THEME, false);
        if (useDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); setTheme(R.style.DarkAppTheme);
        } else {
            Log.i(TAG, "onCreate: applying light theme");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main); Switch toggle = findViewById(R.id.switch1);
        toggle.setChecked(useDarkTheme);
        toggle.setOnCheckedChangeListener((view, isChecked) -> toggleTheme(isChecked));

        super.onCreate(savedInstanceState);
    }

    private void toggleTheme(boolean isChecked) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, isChecked).apply();
        //on recharge pour afficher le theme
        Intent intent = getIntent(); finish(); startActivity(intent);

    }

    public void onClickButtonBoussole(View v) {
        Intent intent = new Intent(MainActivity.this, BoussoleActivity.class);
        startActivity(intent);
    }

    public void onClickButtonMeteo(View v) {

        if (badPermission(v)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
            startActivity(intent);
        }
    }

    public void onClickButtonCarte(View v) {
        if (badPermission(v)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            Intent intent = new Intent(MainActivity.this, CarteActivity.class); startActivity(intent);
        }

    }

    private boolean badPermission(View v) {
        boolean FineLocation = ActivityCompat.checkSelfPermission(v.getContext(),
                                                                  Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean CoarseLocation = ActivityCompat.checkSelfPermission(v.getContext(),
                                                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        return FineLocation && CoarseLocation;
    }
}
