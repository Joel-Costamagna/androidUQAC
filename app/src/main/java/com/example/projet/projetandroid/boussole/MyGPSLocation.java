package com.example.projet.projetandroid.boussole;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/** get the GPS location and show it on the Boussole screen. */
public class MyGPSLocation implements LocationListener {

    /** pour les log */
    private static final String TAG = "gpsLocation";
    private double latitudeCarte;
    private double longitudeCarte;

    /** gère le GPS. */
    private final LocationManager locationManager;
    /** view qui affiche la latitude sous la boussole. */
    TextView latitude;
    /** view qui affiche la longitude sous la boussole. */
    TextView longitude;
    private String mprovider;
    /** view qui affiche l'altitude sous la boussole. */
    //TextView altitude;

    /**
     * constructeur
     *
     * @param context l'activité concernée.
     */
    public MyGPSLocation(final Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);

        assert locationManager != null;
        mprovider = locationManager.getBestProvider(criteria, false);
    }

    /**
     * @param _long la longitude brute
     * @return une string représentant la longitude avec la notation DMS
     */
    private static String parseLongitude(double _long) {
        String[] long_tab     = Location.convert(_long, Location.FORMAT_SECONDS).split(":");
        String   orientation  = "E";
        int      lat_degres   = Math.round(Float.parseFloat(long_tab[0].replace(",", ".")));
        int      lat_minutes  = Math.round(Float.parseFloat(long_tab[1].replace(",", ".")));
        int      lat_secondes = Math.round(Float.parseFloat(long_tab[2].replace(",", ".")));

        if (lat_degres < 0) orientation = "W";
        lat_degres = Math.abs(lat_degres);

        return String.format("%s° %s \' %s\" %s", lat_degres, lat_minutes, lat_secondes, orientation);
    }

    /**
     * @param _lat la latitude brute
     * @return une string représentant la latitude avec la notation DMS
     */
    private static String parseLatitude(double _lat) {
        String[] lat_tab      = Location.convert(_lat, Location.FORMAT_SECONDS).split(":");
        String   orientation  = "N";
        int      lat_degres   = Math.round(Float.parseFloat(lat_tab[0].replace(",", ".")));
        int      lat_minutes  = Math.round(Float.parseFloat(lat_tab[1].replace(",", ".")));
        int      lat_secondes = Math.round(Float.parseFloat(lat_tab[2].replace(",", ".")));

        if (lat_degres < 0) orientation = "S";
        lat_degres = Math.abs(lat_degres);

        return String.format("%s° %s \' %s\" %s", lat_degres, lat_minutes, lat_secondes, orientation);
    }


    /**
     * met à  jour la localisation.
     *
     * @param location la nouvelle localisation à afficher
     */
    @Override public void onLocationChanged(final Location location) {
        Log.i(TAG, "onLocationChanged: " + location);

        latitudeCarte =   location.getLatitude();
        longitudeCarte  =   location.getLongitude();

        longitude.setText(String.format("Long. : %s", parseLongitude(location.getLongitude())));
        latitude.setText(String.format("Lat. : %s", parseLatitude(location.getLatitude())));
        //altitude.setText(String.format("Alt. : %s", Math.round(location.getAltitude())));

    }

    /** démarre le tracking GPS */
    public void start() {
        try {
            Log.i(TAG, "start gps");
            if ((mprovider == null) || mprovider.isEmpty()) {
                mprovider = LocationManager.GPS_PROVIDER;
            }
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
            final Location location = locationManager.getLastKnownLocation(mprovider);

            //affectation lat et lg
            latitudeCarte =   location.getLatitude();
            longitudeCarte  =   location.getLongitude();

            if (location == null) {
                Log.e(TAG, "start: location is null");
                throw new AssertionError("location is null");
            }
            onLocationChanged(location);


        } catch (final SecurityException e) {
            Log.e(TAG, "start: " + e.getLocalizedMessage());
        }
    }

    /** arrete le tracking GPS. */
    public void stop() {
        locationManager.removeUpdates(this);
    }


    @Override public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

    @Override public void onProviderEnabled(final String provider)                                      {}

    @Override public void onProviderDisabled(final String provider)                                     {}

    // recuperer latitude et longitude pour la carte
    public double getLatitudeCarte(){
        start();
        return this.latitudeCarte;
    }

    public double getLongitudeCarte(){
        start();
        return this.longitudeCarte;
    }
}
