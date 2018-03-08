package com.example.projet.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projet.projetandroid.Boussole.BoussoleActivity;
import com.example.projet.projetandroid.Carte.CarteActivity;
import com.example.projet.projetandroid.Meteo.MeteoActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButtonBoussole(View view) {
        Intent intent = new Intent(MainActivity.this, BoussoleActivity.class);
        startActivity(intent);
    }

    public void onClickButtonMeteo(View view) {
        Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
        startActivity(intent);
    }

    public void onClickButtonCarte(View view) {
        Intent intent = new Intent(MainActivity.this, CarteActivity.class);
        startActivity(intent);
    }
}
