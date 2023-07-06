package com.upn.nurena.torres.ExamenFinal;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.helpers.DatabaseHelper;

public class RegistrarDuelistaActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_duelista);

        etNombre = findViewById(R.id.et_nombre);
        btnRegistrar = findViewById(R.id.btn_registrar);

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
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            long rowId = databaseHelper.insertDuelista(nombre);

            if (rowId != -1) {
                Toast.makeText(this, "Duelista registrado: " + nombre, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegistrarDuelistaActivity.this, ListarDuelistaActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error al registrar el duelista", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingresa un nombre válido", Toast.LENGTH_SHORT).show();
        }
    }

}
