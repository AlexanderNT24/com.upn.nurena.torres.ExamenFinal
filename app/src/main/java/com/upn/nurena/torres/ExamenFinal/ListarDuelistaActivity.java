package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        // Configurar el listener para el clic en el ListView
        listViewDuelistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreDuelista = listaDuelistas.get(position);
                Intent intent = new Intent(ListarDuelistaActivity.this, DetalleDuelistaActivity.class);
                intent.putExtra("nombreDuelista", nombreDuelista);
                startActivity(intent);
            }
        });
    }

}
