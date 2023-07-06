package com.upn.nurena.torres.ExamenFinal.entities;

import java.util.ArrayList;
import java.util.List;

public class Duelista {
    private int id;
    private String nombre;
    private List<Carta> cartas;

    public Duelista(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.cartas = new ArrayList<>();
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
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
    public int getId(){return id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
