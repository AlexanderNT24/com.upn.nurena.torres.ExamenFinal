package com.upn.nurena.torres.ExamenFinal.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "duelistas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DUELISTAS = "duelistas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";

    private static final String TABLE_CARTAS = "cartas";
    private static final String COLUMN_ID_CARTA = "id_carta";
    private static final String COLUMN_NOMBRE_CARTA = "nombre_carta";
    private static final String COLUMN_PUNTOS_ATAQUE = "puntos_ataque";
    private static final String COLUMN_PUNTOS_DEFENSA = "puntos_defensa";
    private static final String COLUMN_ID_DUELISTA = "id_duelista";
    private static final String COLUMN_IMAGEN = "imagen";
    private static final String COLUMN_LATITUD = "latitud";
    private static final String COLUMN_LONGITUD = "longitud";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableDuelistasQuery = "CREATE TABLE " + TABLE_DUELISTAS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT" +
                ")";
        db.execSQL(createTableDuelistasQuery);

        String createTableCartasQuery = "CREATE TABLE " + TABLE_CARTAS + "(" +
                COLUMN_ID_CARTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE_CARTA + " TEXT, " +
                COLUMN_PUNTOS_ATAQUE + " REAL, " +
                COLUMN_PUNTOS_DEFENSA + " REAL, " +
                COLUMN_ID_DUELISTA + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ID_DUELISTA + ") REFERENCES " + TABLE_DUELISTAS + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(createTableCartasQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUELISTAS);
        onCreate(db);
    }

    public long insertDuelista(String nombre) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        return db.insert(TABLE_DUELISTAS, null, values);
    }

    public long insertCarta(Carta carta, int idDuelista) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_CARTA, carta.getNombre());
        values.put(COLUMN_PUNTOS_ATAQUE, carta.getPuntosAtaque());
        values.put(COLUMN_PUNTOS_DEFENSA, carta.getPuntosDefensa());
        values.put(COLUMN_ID_DUELISTA, idDuelista);
        return db.insert(TABLE_CARTAS, null, values);
    }

    public ArrayList<Duelista> getAllDuelistas() {
        ArrayList<Duelista> duelistas = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DUELISTAS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));

                Duelista duelista = new Duelista(id, nombre);
                duelista.setCartas(obtenerCartasDuelista(db, id)); // Obtener las cartas del duelista desde la base de datos

                duelistas.add(duelista);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return duelistas;
    }

    private ArrayList<Carta> obtenerCartasDuelista(SQLiteDatabase db, int idDuelista) {
        ArrayList<Carta> cartas = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARTAS + " WHERE " + COLUMN_ID_DUELISTA + " = " + idDuelista, null);

        if (cursor.moveToFirst()) {
            do {
                int idCarta = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CARTA));
                String nombreCarta = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_CARTA));
                int puntosAtaque = cursor.getInt(cursor.getColumnIndex(COLUMN_PUNTOS_ATAQUE));
                int puntosDefensa = cursor.getInt(cursor.getColumnIndex(COLUMN_PUNTOS_DEFENSA));
                String imagen = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEN));
                double latitud = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUD));
                double longitud = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUD));

                Carta carta = new Carta(idCarta, nombreCarta, 1,puntosAtaque, puntosDefensa, imagen, latitud, longitud);
                cartas.add(carta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cartas;
    }


}
