package com.upn.nurena.torres.ExamenFinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.upn.nurena.torres.ExamenFinal.DB.AppDataBase;
import com.upn.nurena.torres.ExamenFinal.SubirImagenes.RspuestaImagen;
import com.upn.nurena.torres.ExamenFinal.SubirImagenes.SolicitarImagen;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.services.CartaDao;
import com.upn.nurena.torres.ExamenFinal.services.SubirImagen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarCartasActivity extends AppCompatActivity implements LocationListener {

    private EditText etNombreCarta;
    private EditText etPuntosAtaque;
    private EditText etPuntosDefensa;
    private Button btnRegistrarCarta;

    private TextView tvCoordenadas;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenUri;
    private ImageView imageView;

    public Double Latitude;
    public Double Longitude;

    private long duelistaId;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cartas);

        imageView = findViewById(R.id.imageView);
        etNombreCarta = findViewById(R.id.et_nombre_carta);
        etPuntosAtaque = findViewById(R.id.et_puntos_ataque);
        etPuntosDefensa = findViewById(R.id.et_puntos_defensa);
        btnRegistrarCarta = findViewById(R.id.btn_registrar_carta);
        tvCoordenadas = findViewById(R.id.tv_coordenadas);

        //PEDIR PERMISOS DE UBICACION
        if(
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
            requestPermissions(permissions, 3000);

        }
        else {
            // configurar frecuencia de actualización de GPS GPSPROMIDER Y NETWORK
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1, this);
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Log.i("MAIN_APP: Location - ",  "Latitude: " + location.getLatitude());
            if(location != null){
                Log.i("MAIN_APP: Location - ",  "Latitude: " + location.getLatitude());
            }
            else {
                Log.i("MAIN_APP: Location - ",  "Location is null");
            }
        }


        Intent intent = getIntent();
        duelistaId = intent.getLongExtra("id", -1);
        Log.i("Duelista ID", "ID del Duelista: " + duelistaId);

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

        if (!nombreCarta.isEmpty() && !puntosAtaqueStr.isEmpty() && !puntosDefensaStr.isEmpty() && imagenUri != null) {
            int puntosAtaque = Integer.parseInt(puntosAtaqueStr);
            int puntosDefensa = Integer.parseInt(puntosDefensaStr);

            String imagenBase64 = obtenerImagenBase64();


            // Crear una instancia de la carta con los datos ingresados
            final Carta carta = new Carta(nombreCarta, puntosAtaque, puntosDefensa, imagenBase64, Latitude, Longitude, duelistaId);

            // Ejecutar la inserción en un hilo separado utilizando AsyncTask
            new AsyncTask<Void, Void, Long>() {
                @Override
                protected Long doInBackground(Void... voids) {
                    // Obtener la instancia de la base de datos y el DAO correspondiente
                    AppDataBase appDatabase = AppDataBase.getInstance(getApplicationContext());
                    CartaDao cartaDao = appDatabase.cartaDao();

                    // Insertar la carta en la base de datos
                    return cartaDao.insertCarta(carta);
                }

                @Override
                protected void onPostExecute(Long cartaId) {
                    if (cartaId > 0) {
                        Toast.makeText(RegistrarCartasActivity.this, "Carta registrada exitosamente", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(RegistrarCartasActivity.this, "Error al registrar la carta", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        } else {
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
        }
    }


    private void limpiarCampos() {
        etNombreCarta.setText("");
        etPuntosAtaque.setText("");
        etPuntosDefensa.setText("");
        imageView.setImageDrawable(null);
        imagenUri = null;
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
                imageView.setImageBitmap(bitmap);
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
    public void onLocationChanged(Location location) {
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();

        String coordenadas = "Latitud: " + Latitude + ", Longitud: " + Longitude;
        tvCoordenadas.setText(coordenadas);
    }

}


