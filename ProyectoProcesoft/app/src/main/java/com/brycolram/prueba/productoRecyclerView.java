package com.brycolram.prueba;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class productoRecyclerView extends RecyclerView.Adapter<productoRecyclerView.ProductoViewHolder> {

    Context context;
    ArrayList<productosDTO> arrayList;

    public productoRecyclerView(Context context, ArrayList<productosDTO> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public productoRecyclerView.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto,viewGroup, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productoRecyclerView.ProductoViewHolder holder, int position) {
        final productosDTO productosDTO = arrayList.get(position);

        holder.etDescripcion.setText(productosDTO.getDescripcion());
        holder.etDireccion.setText(productosDTO.getDireccion());
        holder.etCiudad.setText(productosDTO.getCiudad());
        holder.etTelefono.setText(productosDTO.getTelefono());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView etDescripcion, etDireccion, etCiudad, etTelefono;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            etDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            etDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            etCiudad = (TextView) itemView.findViewById(R.id.tvCiudad);
            etTelefono = (TextView) itemView.findViewById(R.id.tvTelefono);
        }
    }
}
