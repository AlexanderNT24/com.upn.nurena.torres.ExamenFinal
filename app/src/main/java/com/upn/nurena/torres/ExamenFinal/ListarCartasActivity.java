package com.upn.nurena.torres.ExamenFinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upn.nurena.torres.ExamenFinal.DB.AppDataBase;
import com.upn.nurena.torres.ExamenFinal.adapters.CartaAdapter;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.services.CartaDao;

import java.util.ArrayList;
import java.util.List;

public class ListarCartasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCartas;
    private CartaAdapter adapter;
    private CartaDao cartaDao;

    private long idDuelista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cartas);

        cartaDao = AppDataBase.getInstance(getApplicationContext()).cartaDao();

        recyclerViewCartas = findViewById(R.id.recycler_view_cartas);
        recyclerViewCartas.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador de cartas
        adapter = new CartaAdapter(this, new ArrayList<>());
        recyclerViewCartas.setAdapter(adapter);

        // Obtener las cartas desde la base de datos
        obtenerCartas();
    }

    private void obtenerCartas() {
        idDuelista = getIntent().getLongExtra("id", -1);
        obtenerCartasFromDatabase(idDuelista);
    }

    private void obtenerCartasFromDatabase(long idDuelista) {
        AsyncTask<Long, Void, ArrayList<Carta>> task = new AsyncTask<Long, Void, ArrayList<Carta>>() {
            @Override
            protected ArrayList<Carta> doInBackground(Long... params) {
                long duelistaId = params[0];
                Log.i("ListarCartasActivity", "ID del Duelista: " + duelistaId);
                List<Carta> cartasList = cartaDao.getCartasByDuelistaId(duelistaId);
                ArrayList<Carta> cartas = new ArrayList<>(cartasList);
                return cartas;
            }

            @Override
            protected void onPostExecute(ArrayList<Carta> cartas) {
                adapter.setCartas(cartas);
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(idDuelista);
    }

}

