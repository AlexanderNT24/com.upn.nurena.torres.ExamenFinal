package com.upn.nurena.torres.ExamenFinal.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.upn.nurena.torres.ExamenFinal.entities.Carta;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;
import com.upn.nurena.torres.ExamenFinal.services.CartaDao;
import com.upn.nurena.torres.ExamenFinal.services.DuelistaDao;

@Database(entities = {Duelista.class, Carta.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;

    public abstract DuelistaDao duelistaDao();
    public abstract CartaDao cartaDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}