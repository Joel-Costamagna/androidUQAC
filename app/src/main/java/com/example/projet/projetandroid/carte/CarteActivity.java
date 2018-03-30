package com.example.projet.projetandroid.carte;

import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.projet.projetandroid.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class CarteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "Carte";
    private MapView mMapView;
    private MyGPSLocation myGPSLocation;
    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carte);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        //Recuperer les coordonnees a partir de GPSLocation
        myGPSLocation = new MyGPSLocation(this, () -> {
            Log.i(TAG, "onCreate coordonnees");
            this.latitude = myGPSLocation.getLatitude();
            this.longitude = myGPSLocation.getLongitude();
        });
        myGPSLocation.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSavedInstanceState");
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i(TAG, "onMapReady");

        //verification de la permission et affichage du bouton de localisation de map
        //todo: code a decommenter si la localisation de GPSLocalisation fonctionne pas
        //--------------------------------------------------------------------------------------------------------------------------------
        // if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        // && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        //      map.setMyLocationEnabled(true);
        // }
        //---------------------------------------------------------------------------------------------------------------------------------
        map.addMarker(new MarkerOptions().position(new LatLng(this.latitude,this.longitude)).title("Ma position"));
       LatLng position = new LatLng(this.latitude,this.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory");
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}