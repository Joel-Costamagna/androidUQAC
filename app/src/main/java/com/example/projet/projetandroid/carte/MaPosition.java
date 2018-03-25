package com.example.projet.projetandroid.carte;

/**
 * Created by KEITH TOWN on 3/24/2018.
 */


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by KEITH TOWN on 3/18/2018.
 */

public class MaPosition extends Service implements LocationListener {

    private final Context         context;
    protected     LocationManager locationManager;
    boolean isGPSEnabled     = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation   = false;
    Location location;

    public MaPosition() {
        this.context = this;
    }

    public MaPosition(Context context) {
        Log.i("map", "MaPosition: ");
        this.context = context;
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        super.onCreate();
    }

    // creation d'une methode GetLocation
    public Location getLocation() {
        Log.i("map", "getLocation: ");
        //   locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        //   isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        //  isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

        if ((ContextCompat.checkSelfPermission(context,
                                               Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) || (ContextCompat
                                                                                                                                           .checkSelfPermission(
                                                                                                                                                   context,
                                                                                                                                                   Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            // trouver la localisation a partir du gps
            if (isGPSEnabled) {
                Log.i("map", "GPSEnabled: ");
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
                    if (location != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
            //trouver la localisation a partir de reseau
            if (location == null) {
                if (isNetworkEnabled) {
                    Log.i("map", "NetworkEnabled: ");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
                    if (location != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }

            }
        }


        return location;
    }

    //les methodes par defaut pour implementer LocationListener
    public void onLocationChanged(Location location) {

    }

    public void onStatusChanged(String Provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String Provider) {

    }

    public void onProviderDisabled(String Provider) {

    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
