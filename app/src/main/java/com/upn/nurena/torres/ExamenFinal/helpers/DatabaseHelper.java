package com.upn.nurena.torres.ExamenFinal.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "duelistas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DUELISTAS = "duelistas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_DUELISTAS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUELISTAS);
        onCreate(db);
    }

    public long insertDuelista(String nombre) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        return db.insert(TABLE_DUELISTAS, null, values);
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
                // Aquí puedes cargar las cartas del duelista desde la base de datos si es necesario
                // duelista.setCartas(obtenerCartasDuelista(id));

                duelistas.add(duelista);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return duelistas;
    }
}
