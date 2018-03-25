package com.example.projet.projetandroid.carte;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.example.projet.projetandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: CarteActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carte);
        if (ActivityCompat.checkSelfPermission(this,
                                               Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }


        //Map
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        MapsInitializer.initialize(this);
        mapView.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: ");

        gps = new MyGPSLocation(this, () -> {
            this.latitude = gps.getLatitude();
            this.longitude = gps.getLongitude();
        });
        mMap = googleMap;
        LatLng here = new LatLng(this.latitude, this.longitude);
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
