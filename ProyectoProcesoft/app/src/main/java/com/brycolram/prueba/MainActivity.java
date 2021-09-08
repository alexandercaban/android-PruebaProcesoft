package com.brycolram.prueba;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public MapView mapa;
    public MapController mapacontroller;
    List<productosDTO> listObjetos;
    private FirebaseAuth firebaseAuth;

    ArrayList<productosDTO> productosDTOS = new ArrayList<>();
    productosDTO objProductosDTO = new productosDTO();

    EditText etDescripcion, etCiudad, etTelefono, etDireccion;
    Button btnRegistro, btnGestion, btnCerrarSesion, btnGrabar;
    Double Longitud = -76.5205;
    Double Latitud = 3.42158;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDescripcion = findViewById(R.id.idDescripcion);
        etCiudad = findViewById(R.id.idCiudad);
        etTelefono = findViewById(R.id.idTelefono);
        etDireccion = findViewById(R.id.idDireccion);
        btnRegistro = findViewById(R.id.idRegistrar);
        btnGestion = findViewById(R.id.idDespacho);
        btnCerrarSesion = findViewById(R.id.idCerrarSesion);


        firebaseAuth = FirebaseAuth.getInstance();


        GeoPoint referencia = new GeoPoint(Latitud, Longitud);

        mapa = findViewById(R.id.mapa);


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


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });


        btnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objIntent = new Intent(getApplicationContext(), activity_despacho.class);
                startActivity(objIntent);
            }
        });


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrar();
            }
        });


    }

    private void cerrar() {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void guardarDatos() {

        objProductosDTO.setDescripcion(etDescripcion.getText().toString());
        objProductosDTO.setCiudad(etCiudad.getText().toString());
        objProductosDTO.setTelefono(etTelefono.getText().toString());
        objProductosDTO.setDireccion(etDireccion.getText().toString());
        objProductosDTO.setLatitud(Latitud.toString());
        objProductosDTO.setLongitud(Longitud.toString());

        productosDTOS.add(objProductosDTO);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(productosDTOS);
        prefsEditor.putString("listObjetos", json);
        prefsEditor.commit();
    }


}
