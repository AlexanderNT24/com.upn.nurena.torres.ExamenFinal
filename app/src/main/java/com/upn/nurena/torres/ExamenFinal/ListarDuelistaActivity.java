package com.upn.nurena.torres.ExamenFinal;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.adapters.DuelistaAdapter;

import java.util.ArrayList;

public class ListarDuelistaActivity extends AppCompatActivity {

    private ListView listViewDuelistas;
    private ArrayList<String> listaDuelistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_duelista);

        listViewDuelistas = findViewById(R.id.list_view_duelistas);
        listaDuelistas = new ArrayList<>();

        // Ejemplo: Agregar duelistas a la lista
        listaDuelistas.add("Duelista 1");
        listaDuelistas.add("Duelista 2");
        listaDuelistas.add("Duelista 3");

        DuelistaAdapter adapter = new DuelistaAdapter(this, listaDuelistas);
        listViewDuelistas.setAdapter(adapter);
    }
}
