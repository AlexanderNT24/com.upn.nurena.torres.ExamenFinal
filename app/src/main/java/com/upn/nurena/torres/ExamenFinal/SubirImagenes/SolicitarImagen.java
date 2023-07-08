package com.upn.nurena.torres.ExamenFinal.SubirImagenes;

public class SolicitarImagen {
    private String base64Image;

    public SolicitarImagen(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
