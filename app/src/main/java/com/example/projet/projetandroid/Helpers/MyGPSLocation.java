package com.example.projet.projetandroid.Helpers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


/**
 * get the GPS location and show it on the Boussole screen.
 */
public class MyGPSLocation implements LocationListener {

    private static final String TAG = "gpsLocation";

    /**
     * gère le GPS.
     */
    private final LocationManager locationManager;

    private String mprovider;

    /**
     * La latitude brute de l'endroit ou on est.
     */
    private double latitude;
    /**
     * La longitude brute de l'endroid ou on est.
     */
    private double longitude;

    private Runnable onLocationChangedEvent;

    /**
     * constructeur
     *
     * @param context                l'activité concernée.
     * @param onLocationChangedEvent la fonction qui est appelée à chaque changement de location.
     */
    public MyGPSLocation(final Context context, Runnable onLocationChangedEvent) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);

        assert locationManager != null;
        mprovider = locationManager.getBestProvider(criteria, false);
        this.onLocationChangedEvent = onLocationChangedEvent;
    }

    /**
     * @param _long la longitudeView brute
     * @return une string représentant la longitudeView avec la notation DMS
     */
    public static String parseLongitude(double _long) {
        String[] long_tab     = Location.convert(_long, Location.FORMAT_SECONDS).split(":");
        String   orientation  = "E";
        int      lat_degres   = Math.round(Float.parseFloat(long_tab[0].replace(",", ".")));
        int      lat_minutes  = Math.round(Float.parseFloat(long_tab[1].replace(",", ".")));
        int      lat_secondes = Math.round(Float.parseFloat(long_tab[2].replace(",", ".")));

        if (lat_degres < 0) { orientation = "W"; }
        lat_degres = Math.abs(lat_degres);

        return String.format("%s° %s \' %s\" %s",
                             lat_degres,
                             lat_minutes,
                             lat_secondes,
                             orientation);
    }

    /**
     * @param _lat la latitudeView brute
     * @return une string représentant la latitudeView avec la notation DMS
     */
    public static String parseLatitude(double _lat) {
        String[] lat_tab      = Location.convert(_lat, Location.FORMAT_SECONDS).split(":");
        String   orientation  = "N";
        int      lat_degres   = Math.round(Float.parseFloat(lat_tab[0].replace(",", ".")));
        int      lat_minutes  = Math.round(Float.parseFloat(lat_tab[1].replace(",", ".")));
        int      lat_secondes = Math.round(Float.parseFloat(lat_tab[2].replace(",", ".")));

        if (lat_degres < 0) { orientation = "S"; }
        lat_degres = Math.abs(lat_degres);

        return String.format("%s° %s \' %s\" %s",
                             lat_degres,
                             lat_minutes,
                             lat_secondes,
                             orientation);
    }


    /**
     * met à  jour la localisation.
     *
     * @param location la nouvelle localisation à afficher
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: " + location); try {
            this.longitude = location.getLongitude(); this.latitude = location.getLatitude();
            this.onLocationChangedEvent.run();
        } catch (Exception e) {
            Log.e(TAG, "onLocationChanged: " + e.getLocalizedMessage(), e);
        }
    }


    /**
     * démarre le tracking GPS
     */
    public void start() {
        try {
            Log.i(TAG, "start gps");
            if ((mprovider == null) || mprovider.isEmpty()) {
                mprovider = LocationManager.GPS_PROVIDER;
            }
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
            final Location location = locationManager.getLastKnownLocation(mprovider);

            assert location != null : "start: location is null";

            onLocationChanged(location);


        } catch (final SecurityException e) {
            Log.e(TAG, "start: " + e.getLocalizedMessage());
        }
    }

    /**
     * arrete le tracking GPS.
     */
    public void stop() {
        locationManager.removeUpdates(this);
    }


    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

    @Override
    public void onProviderEnabled(final String provider) {}

    @Override
    public void onProviderDisabled(final String provider) {}

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
