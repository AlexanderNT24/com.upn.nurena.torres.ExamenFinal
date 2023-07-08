package com.upn.nurena.torres.ExamenFinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.DB.AppDataBase;
import com.upn.nurena.torres.ExamenFinal.adapters.DuelistaAdapter;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ListarDuelistaActivity extends AppCompatActivity implements DuelistaAdapter.OnDuelistaClickListener {

    private ListView listViewDuelistas;
    private DuelistaAdapter duelistaAdapter;
    private DuelistaDao duelistaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_duelista);

        listViewDuelistas = findViewById(R.id.list_view_duelistas);
        duelistaDao = AppDataBase.getInstance(getApplicationContext()).duelistaDao();

        // Obtener las cartas registradas desde la base de datos
        obtenerDuelistasRegistrados();
    }

    private void obtenerDuelistasRegistrados() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Duelista> duelistas = duelistaDao.getAllDuelistas();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        duelistaAdapter = new DuelistaAdapter(ListarDuelistaActivity.this, new ArrayList<>(duelistas));
                        duelistaAdapter.setOnDuelistaClickListener(ListarDuelistaActivity.this);
                        listViewDuelistas.setAdapter(duelistaAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onDuelistaClick(Duelista duelista) {
        long duelistaId = duelista.getId();
        String nombreDuelista = duelista.getNombre();

        Log.i("ListarDuelistaActivity", "ID del Duelista: " + duelistaId);

        Intent intent = new Intent(ListarDuelistaActivity.this, DetalleDuelistaActivity.class);
        intent.putExtra("id", duelistaId);
        intent.putExtra("nombreDuelista", nombreDuelista);
        startActivity(intent);
    }

}









