package com.upn.nurena.torres.ExamenFinal.services;
import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CartaDao {
    @Query("SELECT * FROM cartas WHERE duelistaId = :duelistaId")
    List<Carta> getCartasByDuelistaId(long duelistaId);

    @Insert
    long insertCarta(Carta carta);

    @Query("SELECT * FROM cartas WHERE id = :cartaId")
    Carta getCartaById(long cartaId);

    @Query("DELETE FROM cartas")
    void deleteAllCartas();

    @Insert
    void insertCartas(List<Carta> cartas);
}