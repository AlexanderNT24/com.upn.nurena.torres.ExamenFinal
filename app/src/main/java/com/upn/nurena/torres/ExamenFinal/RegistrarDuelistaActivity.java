package com.upn.nurena.torres.ExamenFinal;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.DB.AppDataBase;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaApiService;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrarDuelistaActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnRegistrar;
    private DuelistaDao duelistaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_duelista);

        etNombre = findViewById(R.id.et_nombre);
        btnRegistrar = findViewById(R.id.btn_registrar);
        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        duelistaDao = db.duelistaDao();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDuelista();
            }
        });
    }

    private void registrarDuelista() {
        String nombre = etNombre.getText().toString().trim();

        if (!nombre.isEmpty()) {
            Duelista duelista = new Duelista(nombre);

            new InsertDuelistaTask().execute(duelista);
            Toast.makeText(this, "Registrado con exito", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Por favor ingresa un nombre válido", Toast.LENGTH_SHORT).show();
        }
    }
    private class InsertDuelistaTask extends AsyncTask<Duelista, Void, Long> {
        private Duelista duelista;

        @Override
        protected Long doInBackground(Duelista... duelistas) {
            duelista = duelistas[0];
            return duelistaDao.insertDuelista(duelistas[0]);
        }


    }

}
