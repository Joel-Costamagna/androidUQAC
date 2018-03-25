package com.example.projet.projetandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.projet.projetandroid.boussole.BoussoleActivity;
import com.example.projet.projetandroid.carte.CarteActivity;
import com.example.projet.projetandroid.meteo.MeteoActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButtonBoussole(View v) {
        Intent intent = new Intent(MainActivity.this, BoussoleActivity.class);
        startActivity(intent);
    }

    public void onClickButtonMeteo(View v) {
        if (ActivityCompat.checkSelfPermission(v.getContext(),
                                               Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                                                                                                                                         .checkSelfPermission(
                                                                                                                                                 v.getContext(),
                                                                                                                                                 Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
            startActivity(intent);
        }
    }

    public void onClickButtonCarte(View v) {
        Intent intent = new Intent(MainActivity.this, CarteActivity.class);
        startActivity(intent);
    }
}
