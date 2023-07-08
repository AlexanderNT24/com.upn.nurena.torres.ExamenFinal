package com.upn.nurena.torres.ExamenFinal.services;

import com.upn.nurena.torres.ExamenFinal.SubirImagenes.RspuestaImagen;
import com.upn.nurena.torres.ExamenFinal.SubirImagenes.SolicitarImagen;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SubirImagen {
    @POST("image")
    Call<RspuestaImagen> uploadImage(@Body SolicitarImagen request);
}
