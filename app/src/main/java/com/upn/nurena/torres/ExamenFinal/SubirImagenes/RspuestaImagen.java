package com.upn.nurena.torres.ExamenFinal.SubirImagenes;

import com.google.gson.annotations.SerializedName;

public class RspuestaImagen {
    @SerializedName("url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
}
