package com.upn.nurena.torres.ExamenFinal.services;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
public interface DuelistaApiService {
    String url="duelista";

    @POST(url)
    Call<Duelista> create(@Body Duelista duelista);

    @GET(url)
    Call<List<Duelista>> getDuelistas();

}
