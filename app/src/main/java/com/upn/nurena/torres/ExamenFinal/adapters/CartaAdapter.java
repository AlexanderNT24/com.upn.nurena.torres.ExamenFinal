package com.upn.nurena.torres.ExamenFinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.upn.nurena.torres.ExamenFinal.R;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import java.util.ArrayList;

public class CartaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Carta> cartas;

    public CartaAdapter(Context context, ArrayList<Carta> cartas) {
        this.context = context;
        this.cartas = cartas;
    }

    @Override
    public int getCount() {
        return cartas.size();
    }

    @Override
    public Object getItem(int position) {
        return cartas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_carta, parent, false);
        }

        TextView tvNombreCarta = convertView.findViewById(R.id.tv_nombre_carta);
        TextView tvPuntosAtaque = convertView.findViewById(R.id.tv_puntos_ataque);
        TextView tvPuntosDefensa = convertView.findViewById(R.id.tv_puntos_defensa);

        Carta carta = cartas.get(position);

        tvNombreCarta.setText(carta.getNombre());
        tvPuntosAtaque.setText(String.valueOf(carta.getPuntosAtaque()));
        tvPuntosDefensa.setText(String.valueOf(carta.getPuntosDefensa()));

        return convertView;
    }
}
