package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRegistrarDuelista;
    private Button btnRegistrarCarta;
    private Button btnVerDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrarDuelista = findViewById(R.id.btn_registrar_duelista);
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);
        btnVerDetalle = findViewById(R.id.btn_ver_detalle);

        btnRegistrarDuelista.setOnClickListener(v -> registrarDuelista());
        btnRegistrarCarta.setOnClickListener(v -> registrarCarta());
        btnVerDetalle.setOnClickListener(v -> listar());
    }


    private void registrarDuelista() {
        Intent intent = new Intent(MainActivity.this, RegistrarDuelista.class);
        startActivity(intent);
    }

    private void registrarCarta() {

        Toast.makeText(this, "Registrar Carta", Toast.LENGTH_SHORT).show();
    }

    private void listar() {
        Toast.makeText(this, "Ver Detalle de Carta", Toast.LENGTH_SHORT).show();
    }
}
