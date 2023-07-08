package com.upn.nurena.torres.ExamenFinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upn.nurena.torres.ExamenFinal.R;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import java.util.ArrayList;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.CartaViewHolder> {

    private Context context;
    private ArrayList<Carta> cartas;

    public CartaAdapter(Context context, ArrayList<Carta> cartas) {
        this.context = context;
        this.cartas = cartas;
    }

    @NonNull
    @Override
    public CartaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carta, parent, false);
        return new CartaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartaViewHolder holder, int position) {
        Carta carta = cartas.get(position);
        holder.bind(carta);
    }

    @Override
    public int getItemCount() {
        return cartas.size();
    }

    public class CartaViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombreCarta;
        private TextView tvPuntosAtaque;
        private TextView tvPuntosDefensa;
        private ImageView ivImagenCarta;

        public CartaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCarta = itemView.findViewById(R.id.tv_nombre_carta);
            tvPuntosAtaque = itemView.findViewById(R.id.tv_puntos_ataque);
            tvPuntosDefensa = itemView.findViewById(R.id.tv_puntos_defensa);
            ivImagenCarta = itemView.findViewById(R.id.iv_imagen_carta);
        }

        public void bind(Carta carta) {
            tvNombreCarta.setText(carta.getNombre());
            tvPuntosAtaque.setText(String.valueOf(carta.getPuntosAtaque()));
            tvPuntosDefensa.setText(String.valueOf(carta.getPuntosDefensa()));

            // Cargar la imagen de la carta utilizando Picasso
            Picasso.get().load(carta.getImagen()).into(ivImagenCarta);
        }
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
}


