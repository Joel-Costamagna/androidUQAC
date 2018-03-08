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
class MyGPSLocation implements LocationListener {

    /** pour les log */
    private static final String TAG = "gpsLocation";

    /** gère le GPS. */
    private final LocationManager locationManager;
    private final String          mprovider;
    /** view qui affiche la latitude sous la boussole. */
    TextView latitude;
    /** view qui affiche la longitude sous la boussole. */
    TextView longitude;
    /** view qui affiche l'altitude sous la boussole. */
    TextView altitude;

    /**
     * constructeur
     *
     * @param context l'activité concernée.
     */
    MyGPSLocation(final Context context) {


        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);

        assert locationManager != null;
        mprovider = locationManager.getBestProvider(criteria, false);
    }

    /**
     * met à  jour la localisation.
     *
     * @param location la nouvelle localisation à afficher
     */
    @Override public void onLocationChanged(final Location location) {
        Log.i(TAG, "onLocationChanged: " + location);
        longitude.setText(String.format("Long. : %s", location.getLongitude()));
        latitude.setText(String.format("Lat. : %s", location.getLatitude()));
        altitude.setText(String.format("Alt. : %s", location.getAltitude()));

    }

    /** démarre le tracking GPS */
    void start() {
        try {
            Log.i(TAG, "start gps");
            if ((mprovider == null) || mprovider.isEmpty()) return;

            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
            final Location location = locationManager.getLastKnownLocation(mprovider);
            assert location != null;
            onLocationChanged(location);


        } catch (final SecurityException e) {
            Log.e(TAG, "start: " + e.getLocalizedMessage());
        }
    }

    /** arrete le tracking GPS. */
    void stop() {
        locationManager.removeUpdates(this);
    }


    @Override public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

    @Override public void onProviderEnabled(final String provider)                                      {}

    @Override public void onProviderDisabled(final String provider)                                     {}
}
