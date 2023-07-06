package com.upn.nurena.torres.ExamenFinal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.helpers.DatabaseHelper;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;

public class RegistrarCartasActivity extends AppCompatActivity {

    private EditText etNombreCarta;
    private EditText etPuntosAtaque;
    private EditText etPuntosDefensa;
    private Button btnRegistrarCarta;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cartas);

        etNombreCarta = findViewById(R.id.et_nombre_carta);
        etPuntosAtaque = findViewById(R.id.et_puntos_ataque);
        etPuntosDefensa = findViewById(R.id.et_puntos_defensa);
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);

        databaseHelper = new DatabaseHelper(this);

        btnRegistrarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCarta();
            }
        });
    }

    private void registrarCarta() {
        String nombreCarta = etNombreCarta.getText().toString().trim();
        String puntosAtaqueStr = etPuntosAtaque.getText().toString().trim();
        String puntosDefensaStr = etPuntosDefensa.getText().toString().trim();

        if (!nombreCarta.isEmpty() && !puntosAtaqueStr.isEmpty() && !puntosDefensaStr.isEmpty()) {
            float puntosAtaque = Float.parseFloat(puntosAtaqueStr);
            float puntosDefensa = Float.parseFloat(puntosDefensaStr);

            Carta carta = new Carta(1,nombreCarta,2, (int) puntosAtaque, (int) puntosDefensa,"",1,1);

            long resultado = insertarCartaEnBD(carta);

            if (resultado != -1) {
                Toast.makeText(this, "Carta registrada exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al registrar la carta", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertarCartaEnBD(Carta carta) {

        return 1;
    }


    private void limpiarCampos() {
        etNombreCarta.setText("");
        etPuntosAtaque.setText("");
        etPuntosDefensa.setText("");
    }
}

