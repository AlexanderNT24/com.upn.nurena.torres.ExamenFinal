package com.upn.nurena.torres.ExamenFinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.upn.nurena.torres.ExamenFinal.R;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import java.util.ArrayList;

public class DuelistaAdapter extends ArrayAdapter<Duelista> {

    private ArrayList<Duelista> duelistas;
    private Context context;

    private OnDuelistaClickListener clickListener;

    public DuelistaAdapter(Context context, ArrayList<Duelista> duelistas) {
        super(context, 0, duelistas);
        this.context = context;
        this.duelistas = duelistas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_duelista, parent, false);
        }

        Duelista duelista = duelistas.get(position);
        String nombreDuelista = duelista.getNombre();

        TextView tvNombreDuelista = convertView.findViewById(R.id.tv_nombre_duelista);
        tvNombreDuelista.setText(nombreDuelista);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onDuelistaClick(duelista);
                }
            }
        });

        return convertView;
    }

    public interface OnDuelistaClickListener {
        void onDuelistaClick(Duelista duelista);
    }

    public void setOnDuelistaClickListener(OnDuelistaClickListener listener) {
        this.clickListener = listener;
    }
}


