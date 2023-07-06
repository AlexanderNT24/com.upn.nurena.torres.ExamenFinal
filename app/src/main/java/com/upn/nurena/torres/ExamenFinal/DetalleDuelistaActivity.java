package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleDuelistaActivity extends AppCompatActivity {

    private Button btnRegistrarCarta;
    private int idDuelista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

        // Obtener el ID del duelista de los datos extra del intent
        idDuelista = getIntent().getIntExtra("id", -1);

        // Inicializar el botón Registrar Carta
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);
        btnRegistrarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarCartaActivity();
            }
        });
    }

    private void abrirRegistrarCartaActivity() {
        // Crear un Intent para abrir la actividad RegistrarCartasActivity
        Intent intent = new Intent(DetalleDuelistaActivity.this, RegistrarCartasActivity.class);

        // Pasar el ID del duelista como dato extra al intent
        intent.putExtra("idDuelista", idDuelista);

        // Iniciar la actividad
        startActivity(intent);
    }
}

