package com.upn.nurena.torres.ExamenFinal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upn.nurena.torres.ExamenFinal.helpers.DatabaseHelper;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegistrarCartasActivity extends AppCompatActivity {

    private EditText etNombreCarta;
    private EditText etPuntosAtaque;
    private EditText etPuntosDefensa;
    private Button btnRegistrarCarta;

    private DatabaseHelper databaseHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenUri;



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

        Button btnAbrirGaleria = findViewById(R.id.btn_abrir_galeria);
        btnAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
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

            String imagenBase64 = obtenerImagenBase64();

            Carta carta = new Carta(1, nombreCarta, 2, (int) puntosAtaque, (int) puntosDefensa, imagenBase64, 1, 1);

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

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imagenUri = data.getData();
        }
    }

    private String obtenerImagenBase64() {
        if (imagenUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(imagenUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ""; // Devuelve una cadena vacía en caso de error
    }

}