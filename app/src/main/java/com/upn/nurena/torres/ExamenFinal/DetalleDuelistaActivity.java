package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleDuelistaActivity extends AppCompatActivity {

    private Button btnRegistrarCarta;
    private Button btnVerCartas;
    private TextView tvNombreDuelista;

    private long idDuelista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

        // Obtener el ID del duelista de los datos extra del intent
        idDuelista = getIntent().getLongExtra("id", -1);
        tvNombreDuelista = findViewById(R.id.tv_nombre_duelista);

        // Obtener el nombre del duelista enviado desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombreDuelista = extras.getString("nombreDuelista");
            tvNombreDuelista.setText(nombreDuelista);
        }

        // Inicializar el botón Registrar Carta
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);
        btnRegistrarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarCartaActivity();
            }
        });

        // Inicializar el botón Ver Cartas
        btnVerCartas = findViewById(R.id.btn_ver_cartas);
        btnVerCartas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verCartas();
            }
        });
    }

    private void abrirRegistrarCartaActivity() {
        Log.i("DetalleDuelistaActivity", "ID del Duelista: " + idDuelista);
        // Crear un Intent para abrir la actividad RegistrarCartasActivity
        Intent intent = new Intent(DetalleDuelistaActivity.this, RegistrarCartasActivity.class);
        // Pasar el ID del duelista como dato extra al intent
        intent.putExtra("id", idDuelista);
        // Iniciar la actividad
        startActivity(intent);
    }

    private void verCartas() {
        Log.i("DetalleDuelistaActivity", "Ver Cartas del Duelista: " + idDuelista);
        // Crear un Intent para abrir la actividad ListarCartasActivity
        Intent intent = new Intent(DetalleDuelistaActivity.this, ListarCartasActivity.class);
        // Pasar el ID del duelista como dato extra al intent
        intent.putExtra("id", idDuelista);
        // Iniciar la actividad
        startActivity(intent);
    }
}



