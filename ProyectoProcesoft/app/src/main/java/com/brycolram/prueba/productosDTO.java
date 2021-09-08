package com.brycolram.prueba;

import java.util.ArrayList;

public class productosDTO {

    String  Descripcion;
    String Ciudad;
    String Telefono;
    String Direccion;
    String Longitud;

    public static ArrayList<productosDTO> getArrayList() {
        return arrayList;
    }

    public static ArrayList<productosDTO> arrayList;

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    String Latitud;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }


    public static void setArrayList(ArrayList<productosDTO> arrayLista) {
        arrayList = arrayLista;
    }

}
