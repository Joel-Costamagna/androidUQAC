package com.example.projet.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
        startActivity(intent);
    }

    public void onClickButtonCarte(View v) {
        Intent intent = new Intent(MainActivity.this, CarteActivity.class);
        startActivity(intent);
    }
}
