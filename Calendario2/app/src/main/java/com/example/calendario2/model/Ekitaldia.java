package com.example.calendario2.model;

import com.example.calendario2.shared.EkitaldiMota;
import com.google.type.DateTime;

public class Ekitaldia {

    private String id;
    private String izena;
    private String deskribapena;
    private DateTime hasierakoDataOrdua;
    private DateTime bukaerakoDataOrdua;
    private String sala;
    private double aurrekontua;
    private EkitaldiMota ekitaldiMota;
    private String usuario;
    private String[] sucesos;

    // Constructor
    public Ekitaldia(String id, String izena, String deskribapena, DateTime hasierakoDataOrdua, DateTime bukaerakoDataOrdua, String sala, double aurrekontua, EkitaldiMota ekitaldiMota, String usuario, String[] sucesos) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.hasierakoDataOrdua = hasierakoDataOrdua;
        this.bukaerakoDataOrdua = bukaerakoDataOrdua;
        this.sala = sala;
        this.aurrekontua = aurrekontua;
        this.ekitaldiMota = ekitaldiMota;
        this.usuario = usuario;
        this.sucesos = sucesos;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ekitaldia{" +
                "id='" + id + '\'' +
                ", izena='" + izena + '\'' +
                ", deskribapena='" + deskribapena + '\'' +
                ", hasierakoDataOrdua=" + hasierakoDataOrdua +
                ", bukaerakoDataOrdua=" + bukaerakoDataOrdua +
                ", sala='" + sala + '\'' +
                ", aurrekontua=" + aurrekontua +
                ", ekitaldiMota=" + ekitaldiMota +
                ", usuario='" + usuario + '\'' +
                ", sucesos=" + sucesos +
                '}';
    }


}
