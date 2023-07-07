package com.upn.nurena.torres.ExamenFinal.services;
import com.upn.nurena.torres.ExamenFinal.entities.Duelista;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
public interface DuelistaApiService {
    String url="apiUrl";

    @GET(url)
    Call<List<Duelista>> getAll();

    @GET(url+"/{id}")
    Call<Duelista> find(@Path("id") int id);

    @POST(url)
    Call<Duelista> create(@Body Duelista object);

    @PUT(url+"/{id}")
    Call<Duelista> update(@Path("id") int id, @Body Duelista object);

    @DELETE(url+"/{id}")
    Call<Void> delete(@Path("id") int id);

}
