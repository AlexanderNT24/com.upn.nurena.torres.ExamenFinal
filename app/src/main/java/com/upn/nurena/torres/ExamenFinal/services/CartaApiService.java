package com.upn.nurena.torres.ExamenFinal.services;

import com.upn.nurena.torres.ExamenFinal.entities.Carta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartaApiService {
    @POST("cartas")
    Call<Carta> createCarta(@Body Carta carta);

    @GET("cartas")
    Call<List<Carta>> getCartas();

}
