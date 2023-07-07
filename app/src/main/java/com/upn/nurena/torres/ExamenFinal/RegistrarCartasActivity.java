package com.upn.nurena.torres.ExamenFinal;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegistrarCartasActivity extends AppCompatActivity {

    private EditText etNombreCarta;
    private EditText etPuntosAtaque;
    private EditText etPuntosDefensa;
    private Button btnRegistrarCarta;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenUri;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitud;
    private double longitud;
    private TextView tvCoordenadas;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.imageView);
        setContentView(R.layout.activity_registrar_cartas);
        tvCoordenadas = findViewById(R.id.tv_coordenadas);
        etNombreCarta = findViewById(R.id.et_nombre_carta);
        etPuntosAtaque = findViewById(R.id.et_puntos_ataque);
        etPuntosDefensa = findViewById(R.id.et_puntos_defensa);
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                String coordenadas = "Latitud: " + latitud + ", Longitud: " + longitud;
                tvCoordenadas.setText(coordenadas);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

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

            //Carta carta = new Carta(1, nombreCarta, 2, (int) puntosAtaque, (int) puntosDefensa, imagenBase64, latitud, longitud);

            //long resultado = insertarCartaEnBD(carta);

            Toast.makeText(this, "Carta registrada exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertarCartaEnBD(Carta carta) {

        return 1;
    }

    private void limpiarCampos() {
        Intent intent = new Intent(RegistrarCartasActivity.this, ListarDuelistaActivity.class);
        startActivity(intent);
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

            try {
                InputStream inputStream = getContentResolver().openInputStream(imagenUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }


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
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarActualizacionesUbicacion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detenerActualizacionesUbicacion();
    }

    private void iniciarActualizacionesUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no están concedidos, solicítalos aquí
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void detenerActualizacionesUbicacion() {
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, inicia las actualizaciones de ubicación
                iniciarActualizacionesUbicacion();
            } else {
                // Permiso denegado, muestra un mensaje o realiza una acción alternativa
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
