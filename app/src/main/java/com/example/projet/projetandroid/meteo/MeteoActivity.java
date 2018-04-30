package com.example.projet.projetandroid.meteo;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.example.projet.projetandroid.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import com.example.projet.projetandroid.R;

public class MeteoActivity extends Activity {
    private Coord coord;
    private MyGPSLocation myGPSLocation;
    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView weatherIconTextView;
    private TextView descTextView;
    private TextView descMeteoTextView;
    private Typeface weatherFont;
    private ProgressBar progressBar;
    private final static String API_KEY = "0d580ba5e5cbb7b1c124a5f45a678f82";
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        }
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getAssets(),"fonts/weather.ttf");
        setContentView(R.layout.layout_meteo);
        myGPSLocation = new MyGPSLocation(this, new Runnable() {
            @Override
            public void run() {

            }
        });
        myGPSLocation.start();
        myGPSLocation.stop();
        coord = new Coord(myGPSLocation.getLatitude(),myGPSLocation.getLongitude());
        progressBar = findViewById(R.id.progressBar);
        descTextView = findViewById(R.id.desc); weatherIconTextView = findViewById(R.id.weather_icon);
        cityTextView = findViewById(R.id.city); temperatureTextView = findViewById(R.id.current_temperature);
        descMeteoTextView = findViewById(R.id.descMeteo);
        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO i18n lang code
        final String url ="http://api.openweathermap.org/data/2.5/weather?lat="+coord.getLatitude()+"&lon="+coord.getLongitude()+"&"+getString(R.string.langAPI)+"&appid="+API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try{
                            //Display the progressBar
                            progressBar.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            Base base = gson.fromJson(response,Base.class);
                            Calendar sunset = Calendar.getInstance();
                            Calendar sunrise = Calendar.getInstance();
                            sunset.setTimeInMillis(base.getSys().getSunset() * 1000);
                            sunrise.setTimeInMillis(base.getSys().getSunrise() * 1000);
                            //TODO i18n
                            descMeteoTextView.setText(""+ base.getWeather().get(0).getDescription() + "");
                            descTextView.setText(
                                     "\n"+getString(R.string.humidite)+" : " + base.getMain().getHumidity() + "% , "
                                    + getString(R.string.pression)+" : " + base.getMain().getPressure() + " hPa\n"
                                    + "\n" +getString(R.string.vitesse)+" : " + base.getWind().getSpeed() + " m/s , "
                                    + getString(R.string.direction)+" : " + base.getWind().getDeg() + " \n");
                            temperatureTextView.setText( "" + (Math.round(base.getMain().getTemp() - 273.15)) + " °C\n");
                            cityTextView.setText(""+base.getName());

                            int idActuelle = base.getWeather().get(0).getId();
                            int id = idActuelle / 100;
                            String icon = "";
                            if(idActuelle == 800){
                                long currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                                if(currentTime>=sunrise.get(Calendar.HOUR_OF_DAY) && currentTime<sunset.get(Calendar.HOUR_OF_DAY)) {
                                    icon = getString(R.string.weather_sunny);
                                } else {
                                    icon = getString(R.string.weather_clear_night);
                                }
                            } else {
                                switch(id) {
                                    case 2 : icon = getString(R.string.weather_thunder);
                                        break;
                                    case 3 : icon = getString(R.string.weather_drizzle);
                                        break;
                                    case 7 : icon = getString(R.string.weather_foggy);
                                        break;
                                    case 8 : icon = getString(R.string.weather_cloudy);
                                        break;
                                    case 6 : icon = getString(R.string.weather_snowy);
                                        break;
                                    case 5 : icon = getString(R.string.weather_rainy);
                                        break;
                                }
                            }
                            weatherIconTextView.setText(icon);
                            weatherIconTextView.setTypeface(weatherFont);

                            //Dismiss the progress bar
                            progressBar.setVisibility(View.GONE);
                        }catch (Exception error){cityTextView.setText(response + "\n" + error.getMessage());}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MeteoActivity.this, "Erreur", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public void onClickButtonRechercheVille(View view){

       TextView nomVille = (TextView)findViewById(R.id.change_city);




        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO i18n lang code
        String text = "";
        try {
            text = URLEncoder.encode( nomVille.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String url ="http://api.openweathermap.org/data/2.5/weather?q="+text+"&"+getString(R.string.langAPI)+"&appid="+API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try{
                            //Display the progressBar
                            progressBar.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            Base base = gson.fromJson(response,Base.class);
                            Calendar sunset = Calendar.getInstance();
                            Calendar sunrise = Calendar.getInstance();
                            sunset.setTimeInMillis(base.getSys().getSunset() * 1000);
                            sunrise.setTimeInMillis(base.getSys().getSunrise() * 1000);
                            //TODO i18n
                            descMeteoTextView.setText(""+ base.getWeather().get(0).getDescription() + "");
                            descTextView.setText(
                                    "\n"+getString(R.string.humidite)+" : " + base.getMain().getHumidity() + "% , "
                                            + getString(R.string.pression)+" : " + base.getMain().getPressure() + " hPa\n"
                                            + "\n" +getString(R.string.vitesse)+" : " + base.getWind().getSpeed() + " m/s , "
                                            + getString(R.string.direction)+" : " + base.getWind().getDeg() + " \n");
                            temperatureTextView.setText( "" + (Math.round(base.getMain().getTemp() - 273.15)) + " °C\n");
                            cityTextView.setText(""+base.getName());

                            int idActuelle = base.getWeather().get(0).getId();
                            int id = idActuelle / 100;
                            String icon = "";
                            if(idActuelle == 800){
                                long currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                                if(currentTime>=sunrise.get(Calendar.HOUR_OF_DAY) && currentTime<sunset.get(Calendar.HOUR_OF_DAY)) {
                                    icon = getString(R.string.weather_sunny);
                                } else {
                                    icon = getString(R.string.weather_clear_night);
                                }
                            } else {
                                switch(id) {
                                    case 2 : icon = getString(R.string.weather_thunder);
                                        break;
                                    case 3 : icon = getString(R.string.weather_drizzle);
                                        break;
                                    case 7 : icon = getString(R.string.weather_foggy);
                                        break;
                                    case 8 : icon = getString(R.string.weather_cloudy);
                                        break;
                                    case 6 : icon = getString(R.string.weather_snowy);
                                        break;
                                    case 5 : icon = getString(R.string.weather_rainy);
                                        break;
                                }
                            }
                            weatherIconTextView.setText(icon);
                            weatherIconTextView.setTypeface(weatherFont);

                            //Dismiss the progress bar
                            progressBar.setVisibility(View.GONE);

                        }catch (Exception error){cityTextView.setText(response + "\n" + error.getMessage());}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MeteoActivity.this, "Erreur", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }


}
