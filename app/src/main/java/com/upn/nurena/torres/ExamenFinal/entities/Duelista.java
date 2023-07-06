package com.upn.nurena.torres.ExamenFinal.entities;

import java.util.ArrayList;
import java.util.List;

public class Duelista {
    private String nombre;
    private List<Carta> cartas;

    public Duelista(String nombre) {
        this.nombre = nombre;
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    }

    public List<Carta> getCartas() {
        return cartas;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
