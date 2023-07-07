package com.upn.nurena.torres.ExamenFinal.services;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DuelistaDao {
    @Query("SELECT * FROM duelistas")
    List<Duelista> getAllDuelistas();

    @Insert
    long insertDuelista(Duelista duelista);

    @Query("SELECT * FROM duelistas WHERE id = :duelistaId")
    Duelista getDuelistaById(long duelistaId);

    @Query("DELETE FROM duelistas")
    void deleteAllDuelistas();

    @Insert
    void insertDuelistas(List<Duelista> duelistas);
}