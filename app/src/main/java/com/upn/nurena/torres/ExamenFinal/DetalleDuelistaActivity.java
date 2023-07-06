package com.upn.nurena.torres.ExamenFinal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleDuelistaActivity extends AppCompatActivity {

    private TextView tvNombreDuelista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

        tvNombreDuelista = findViewById(R.id.tv_nombre_duelista);

        // Obtener el nombre del duelista enviado desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombreDuelista = extras.getString("nombreDuelista");
            tvNombreDuelista.setText(nombreDuelista);
        }
    }
}
