package com.example.projet.projetandroid.carte;

import android.Manifest;

import com.example.projet.projetandroid.boussole.*;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;


import com.example.projet.projetandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarteActivity extends Activity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView;
    private static final String TAG = "carte";

    private MyGPSLocation gps ;

    double lattitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("carte", "onCreate: CarteActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carte);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        //todo: recuperation de la position avec ma classe MyGPSLocation de joel
       // todo: gps = new MyGPSLocation(this);

        //todo: recuperer la latittude et la longitude
       // todo: Lattitude = gps.getLatitudeCarte();
      // todo: longitude = gps.getLongitudeCarte();

        Log.i("carte", "latitude: "+lattitude);
        Log.i("carte", "longitude: "+longitude);
        //Map
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        MapsInitializer.initialize(this);
        mapView.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("carte", "onMapReady: ");

        //todo: recuperation de la position avec ma classe MyGPSLocation de joel
       // todo: gps = new MyGPSLocation(this);
        //todo: quand tu actives sa marche pas

        mMap = googleMap;
        LatLng here = new LatLng(43,5465 );
        mMap.addMarker(new MarkerOptions().position(here).title("je suis ici"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(here));

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
