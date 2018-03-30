package com.example.projet.projetandroid.carte;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.example.projet.projetandroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarteActivity extends Activity implements OnMapReadyCallback {
    private static final String TAG = "carte";
    double latitude, longitude;
    private GoogleMap     mMap;
    private MapView       mapView;
    private MyGPSLocation gps;

    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: CarteActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carte);


        //Map
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        MapsInitializer.initialize(this);
        mapView.getMapAsync(this);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: ");
        mMap = googleMap;
        LatLng here = new LatLng(this.latitude, this.longitude);
        mMap.addMarker(new MarkerOptions().position(here).title("je suis ici"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
        //mMap.moveCamera(CameraUpdateFactory.zoomOut());

        mMap.setMyLocationEnabled(true);

    }


    @Override
    protected void onStart() {
        super.onStart();
      /*  gps = new MyGPSLocation(this, () -> {
            Log.i(TAG, "onStart: mise à jour position");
            this.latitude = gps.getLatitude();
            this.longitude = gps.getLongitude();
        });
        gps.start();*/
    }

    @Override
    protected void onPause() {
        super.onPause(); gps.stop();
    }

    @Override
    protected void onResume() {
        super.onResume(); gps.start();
    }

    @Override
    protected void onStop() {
        super.onStop(); gps.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy(); gps.stop();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
