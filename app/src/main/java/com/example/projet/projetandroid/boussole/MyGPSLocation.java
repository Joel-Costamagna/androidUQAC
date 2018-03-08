package com.example.projet.projetandroid.boussole;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/**
 * get the GPS location and show it on the Boussole screen.
 */
class MyGPSLocation implements LocationListener {

    private static final String TAG = "gpsLocation";
    private final LocationManager locationManager;
    TextView latitude  = null;
    TextView longitude = null;
    TextView altitude  = null;
    private String mprovider;

    MyGPSLocation(Context context) {


        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);

        assert locationManager != null;
        mprovider = locationManager.getBestProvider(criteria, false);


    }

    @Override public void onLocationChanged(android.location.Location location) {
        Log.i(TAG, "onLocationChanged: " + location.toString());
        longitude.setText(String.format("Long. : %s", location.getLongitude()));
        latitude.setText(String.format("Lat. : %s", location.getLatitude()));
        altitude.setText(String.format("Alt. : %s", location.getAltitude()));


    }

    void start() throws SecurityException {
        Log.i(TAG, "start gps");
        if (mprovider != null && !mprovider.isEmpty()) {

            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
            if (location != null) {
                onLocationChanged(location);
            }
        }

    }

    void stop() {
        locationManager.removeUpdates(this);
    }


    @Override public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override public void onProviderEnabled(String provider)                          {}

    @Override public void onProviderDisabled(String provider)                         {}
}
