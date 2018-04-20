package com.example.projet.projetandroid.meteo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.example.projet.projetandroid.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

public class MeteoActivity extends Activity {
    private final static String API_KEY = "0d580ba5e5cbb7b1c124a5f45a678f82";
    private static final String TAG     = "MeteoActivity";
    private Coord         coord;
    private MyGPSLocation myGPSLocation;
    private TextView      nameTextView;
    private TextView      countryTextView;
    private TextView      coordTextView;
    private TextView      descTextView;

    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_meteo);
        myGPSLocation = new MyGPSLocation(this, new Runnable() {
            @Override
            public void run() {

            }
        });
        myGPSLocation.start();
        myGPSLocation.stop(); coord = new Coord(myGPSLocation.getLatitude(), myGPSLocation.getLongitude());

        descTextView = findViewById(R.id.desc); nameTextView = findViewById(R.id.name);
        countryTextView = findViewById(R.id.country); coordTextView = findViewById(R.id.coord);
        RequestQueue queue = Volley.newRequestQueue(this); String country_code = Locale.getDefault().getISO3Language();
        if (country_code.isEmpty()) { country_code = "fr"; }
        @SuppressLint("DefaultLocale") final String url = String.format(
                "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&lang=%s&appid=%s",
                coord.getLatitude(),
                coord.getLongitude(),
                country_code,
                API_KEY); Log.i(TAG, "url requete: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            // Display the first 500 characters of the response string.
            try {
                Gson     gson   = new Gson(); Base base = gson.fromJson(response, Base.class);
                Calendar sunset = Calendar.getInstance(); Calendar sunrise = Calendar.getInstance();
                sunset.setTimeInMillis(base.getSys().getSunset() * 1000);
                sunrise.setTimeInMillis(base.getSys().getSunrise() * 1000); descTextView
                        .setText("Meteo : " + base.getWeather().get(0).getDescription() + "\n" + "\nHumidité : " + base
                                .getMain().getHumidity() + "%\n" + "\nPression : " + base.getMain()
                                                                                         .getPressure() + " hPa\n" + "\nTempérature : " + (base
                                                                                                                                                   .getMain()
                                                                                                                                                   .getTemp() - 273.15) + " °C\n" + "\nVitesse de vent : " + base
                                         .getWind().getSpeed() + " m/s\n" + "\nDirection du vent : " + base.getWind()
                                                                                                           .getDeg() + " (" + base
                                         .getWind().getDeg() + ") \n" + "\nLever soleil : " + sunrise
                                         .get(Calendar.HOUR_OF_DAY) + ":" + sunrise.get(Calendar.MINUTE) + ":" + sunrise
                                         .get(Calendar.SECOND) + "\n" + "\nCoucher soleil : " + sunset
                                         .get(Calendar.HOUR_OF_DAY) + ":" + sunset.get(Calendar.MINUTE) + ":" + sunset
                                         .get(Calendar.SECOND) + "\n");
            } catch (Exception error) {
                nameTextView.setText(String.format("%s\n%s", response, error.getMessage()));
            }
        }, error -> Toast.makeText(MeteoActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show());
        queue.add(stringRequest);
    }


}
