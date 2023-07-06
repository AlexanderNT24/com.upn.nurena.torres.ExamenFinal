package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.adapters.CartaAdapter;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;
import com.upn.nurena.torres.ExamenFinal.helpers.DatabaseHelper;

import java.util.ArrayList;

public class DetalleDuelistaActivity extends AppCompatActivity {

    private Button btnRegistrarCarta;
    private TextView tvNombreDuelista;
    private ListView listViewCartas;
    private CartaAdapter adapter;

    private int idDuelista;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

        // Obtener el ID del duelista de los datos extra del intent
        idDuelista = getIntent().getIntExtra("id", -1);
        tvNombreDuelista = findViewById(R.id.tv_nombre_duelista);
        listViewCartas = findViewById(R.id.list_view_cartas);

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

        // Inicializar el DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener todas las cartas del duelista y mostrarlas en el ListView
        ArrayList<Carta> cartas = databaseHelper.obtenerCartasDuelista(idDuelista);
        adapter = new CartaAdapter(this, cartas);
        listViewCartas.setAdapter(adapter);
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
