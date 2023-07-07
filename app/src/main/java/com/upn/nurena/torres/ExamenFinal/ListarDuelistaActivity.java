package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.adapters.DuelistaAdapter;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;


import java.util.ArrayList;

public class ListarDuelistaActivity extends AppCompatActivity {

    private ListView listViewDuelistas;
    private ArrayList<Duelista> listaDuelistas;
    private ArrayAdapter<Duelista> adapter;
    //private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_duelista);

        listViewDuelistas = findViewById(R.id.list_view_duelistas);
        listaDuelistas = new ArrayList<>();
        //databaseHelper = new DatabaseHelper(this);

        // Obtener la lista de duelistas desde la base de datos
        //listaDuelistas = databaseHelper.getAllDuelistas();

        adapter = new DuelistaAdapter(this, listaDuelistas);
        listViewDuelistas.setAdapter(adapter);

        // Configurar el listener para el clic en el ListView
        listViewDuelistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duelista duelista = listaDuelistas.get(position);
                Intent intent = new Intent(ListarDuelistaActivity.this, DetalleDuelistaActivity.class);
                intent.putExtra("id", duelista.getId());
                intent.putExtra("nombreDuelista", duelista.getNombre());
                startActivity(intent);
            }
        });
    }
}


