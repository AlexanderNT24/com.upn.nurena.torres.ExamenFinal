package com.upn.nurena.torres.ExamenFinal;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.DB.AppDataBase;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaApiService;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaDao;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarDuelistaActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnRegistrar;
    private DuelistaDao duelistaDao;
    private DuelistaApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_duelista);

        etNombre = findViewById(R.id.et_nombre);
        btnRegistrar = findViewById(R.id.btn_registrar);
        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        duelistaDao = db.duelistaDao();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://647879cf362560649a2ddb9c.mockapi.io/") // Reemplazar con la URL base de tu API MockAPI
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(DuelistaApiService.class);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDuelista();
            }
        });
    }

    private class InsertDuelistaTask extends AsyncTask<Duelista, Void, Duelista> {
        private Duelista duelista;

        @Override
        protected Duelista doInBackground(Duelista... duelistas) {
            duelista = duelistas[0];

            // Insertar el Duelista en la base de datos local
            long duelistaId = duelistaDao.insertDuelista(duelista);
            duelista.setId(duelistaId);

            // Registrar el Duelista en MockAPI
            try {
                Response<Duelista> response = apiService.create(duelista).execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                Log.e("InsertDuelistaTask", "Error al registrar el Duelista en MockAPI: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Duelista createdDuelista) {
            super.onPostExecute(createdDuelista);

            if (createdDuelista != null) {
                Toast.makeText(RegistrarDuelistaActivity.this, "Duelista registrado", Toast.LENGTH_SHORT).show();
                etNombre.setText("");

                // No es necesario pasar el ID del Duelista a la siguiente actividad
                Intent intent = new Intent(RegistrarDuelistaActivity.this, DetalleDuelistaActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegistrarDuelistaActivity.this, "Error al registrar el Duelista en MockAPI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDuelistasTask extends AsyncTask<Void, Void, List<Duelista>> {
        @Override
        protected List<Duelista> doInBackground(Void... voids) {
            try {
                Response<List<Duelista>> response = apiService.getDuelistas().execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                Log.e("GetDuelistasTask", "Error al obtener los Duelistas de MockAPI: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Duelista> duelistas) {
            super.onPostExecute(duelistas);

            if (duelistas != null) {
                // Actualizar la lista de Duelistas en la base de datos local
                new UpdateDuelistasTask().execute(duelistas);
            } else {
                Toast.makeText(RegistrarDuelistaActivity.this, "Error al obtener los Duelistas de MockAPI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdateDuelistasTask extends AsyncTask<List<Duelista>, Void, Void> {
        @Override
        protected Void doInBackground(List<Duelista>... lists) {
            List<Duelista> duelistas = lists[0];
            duelistaDao.deleteAllDuelistas();
            duelistaDao.insertDuelistas(duelistas);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(RegistrarDuelistaActivity.this, "Datos de Duelistas actualizados", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para iniciar la obtención de los Duelistas
    private void obtenerDuelistas() {
        new GetDuelistasTask().execute();
    }

    // Método para registrar un nuevo Duelista
    private void registrarDuelista() {
        String nombre = etNombre.getText().toString().trim();

        if (!nombre.isEmpty()) {
            Duelista duelista = new Duelista(nombre);

            new InsertDuelistaTask().execute(duelista);
        } else {
            Toast.makeText(this, "Por favor ingresa un nombre válido", Toast.LENGTH_SHORT).show();
        }
    }

}


