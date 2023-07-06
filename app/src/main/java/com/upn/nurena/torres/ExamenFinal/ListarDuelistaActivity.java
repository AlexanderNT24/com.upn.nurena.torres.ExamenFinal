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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_duelista);

        listViewDuelistas = findViewById(R.id.list_view_duelistas);
        listaDuelistas = new ArrayList<>();

        // Ejemplo: Agregar duelistas a la lista
        Duelista duelista1 = new Duelista("Duelista 1");
        duelista1.agregarCarta(new Carta("Carta 1", 1000, 800, "imagen1.jpg", 0.0, 0.0));
        duelista1.agregarCarta(new Carta("Carta 2", 1500, 1200, "imagen2.jpg", 0.0, 0.0));

        Duelista duelista2 = new Duelista("Duelista 2");
        duelista2.agregarCarta(new Carta("Carta 1", 1000, 800, "imagen1.jpg", 0.0, 0.0));
        duelista2.agregarCarta(new Carta("Carta 2", 1500, 1200, "imagen2.jpg", 0.0, 0.0));

        listaDuelistas.add(duelista1);
        listaDuelistas.add(duelista2);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDuelistas);
        listViewDuelistas.setAdapter(adapter);

        // Configurar el listener para el clic en el ListView
        listViewDuelistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duelista duelista = listaDuelistas.get(position);
                Intent intent = new Intent(ListarDuelistaActivity.this, DetalleDuelistaActivity.class);
                intent.putExtra("nombreDuelista", duelista.getNombre());
                startActivity(intent);
            }
        });
    }

}
