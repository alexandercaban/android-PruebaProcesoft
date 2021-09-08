package com.brycolram.prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class activity_despacho extends AppCompatActivity {

    TextView tvDescripcion, tvDireccion, tvCiudad, tvTelefono;
    RecyclerView productoRecycler;
    JSONArray jsonArray;
    productoRecyclerView productoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despacho);


        tvDescripcion = (findViewById(R.id.tvDescripcion));
        tvDireccion = (findViewById(R.id.tvDireccion));
        tvCiudad = (findViewById(R.id.tvCiudad));
        tvTelefono = (findViewById(R.id.tvTelefono));
        productoRecycler  = (findViewById(R.id.idRecyclerProductos));

        productoRecycler.setHasFixedSize(true);
        productoRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        try {
            cargarProductos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos() throws JSONException {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("listObjetos", "");
        //productosDTO productos = gson.fromJson(json, productosDTO.class);

        Type type = new TypeToken<List<productosDTO>>(){}.getType();
        ArrayList<productosDTO> listObjetos = gson.fromJson(json, type);

        productoRecyclerView = new productoRecyclerView(getApplication(), listObjetos);
        productoRecycler.setAdapter(productoRecyclerView);

    }
}
