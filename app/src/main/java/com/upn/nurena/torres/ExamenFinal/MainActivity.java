package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
        btnVerDetalle = findViewById(R.id.btn_ver_detalle);

        btnRegistrarDuelista.setOnClickListener(v -> registrarDuelista());
         btnVerDetalle.setOnClickListener(v -> listar());
    }

    private void registrarDuelista() {
        Intent intent = new Intent(MainActivity.this, RegistrarDuelistaActivity.class);
        startActivity(intent);
    }

    private void listar() {
        Intent intent = new Intent(MainActivity.this, ListarDuelistaActivity.class);
        startActivity(intent);
    }

}
