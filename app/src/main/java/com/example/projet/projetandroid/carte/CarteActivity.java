package com.example.projet.projetandroid.carte;

import com.example.projet.projetandroid.Helpers.MyGPSLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.projet.projetandroid.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * This shows how to create a simple activity with a raw MapView and add a marker to it. This
 * requires forwarding all the important lifecycle methods onto MapView.
 */
public class CarteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG ="Carte" ;
    private MapView mMapView;
    private MyGPSLocation myGPSLocation;
    private double latitude, longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carte);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        //Recuperer les coordonnees de l'utilisateur
        myGPSLocation = new MyGPSLocation(this, () -> {
            Log.i(TAG, "onStart: mise à jour position");
            this.latitude = myGPSLocation.getLatitude();
            this.longitude = myGPSLocation.getLongitude();
        });
        myGPSLocation.start();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(this.latitude,this.longitude)).title("Ma position"));
        LatLng position = new LatLng(this.latitude,this.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}