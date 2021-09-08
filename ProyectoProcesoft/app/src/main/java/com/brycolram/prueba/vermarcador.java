package com.brycolram.prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class vermarcador extends AppCompatActivity {

    public MapView mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermarcador);

        mapa = findViewById(R.id.mapaver);

        if (mapa != null) {
            mapa.onCreate(savedInstanceState);
            mapa.onResume();
            mapa.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);

                    }else{
                        LatLng cali = new LatLng(3.4383, -76.5161);
                        googleMap.addMarker(new MarkerOptions()
                                .position(cali)
                                .title("Cali"));

                        CameraPosition cameraPosition = CameraPosition.builder()
                                .target(cali)
                                .zoom(10)
                                .build();

                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
            });
        }
    }
}
