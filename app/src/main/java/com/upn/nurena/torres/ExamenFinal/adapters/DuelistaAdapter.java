package com.upn.nurena.torres.ExamenFinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.upn.nurena.torres.ExamenFinal.R;

import java.util.ArrayList;

public class DuelistaAdapter extends ArrayAdapter<String> {

    private ArrayList<String> duelistas;
    private Context context;

    public DuelistaAdapter(Context context, ArrayList<String> duelistas) {
        super(context, 0, duelistas);
        this.context = context;
        this.duelistas = duelistas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_duelista, parent, false);
        }

        String nombreDuelista = duelistas.get(position);

        TextView tvNombreDuelista = convertView.findViewById(R.id.tv_nombre_duelista);
        tvNombreDuelista.setText(nombreDuelista);

        return convertView;
    }
}
