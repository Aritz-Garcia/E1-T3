package com.example.calendario2.model;

import com.example.calendario2.shared.EkitaldiMota;
import com.google.firebase.Timestamp;
import com.google.type.DateTime;

import java.util.List;

public class Ekitaldia {

    private String id;
    private String izena;
    private String deskribapena;
    private Timestamp hasierakoDataOrdua;
    private Timestamp bukaerakoDataOrdua;
    private String gela;
    private double aurrekontua;
    private EkitaldiMota ekitaldiMota;
    private String usuario;
    private List<String> gerataerak;

    // Constructor
    public Ekitaldia() {}

    public Ekitaldia(String id, String izena, String deskribapena, Timestamp hasierakoDataOrdua, Timestamp bukaerakoDataOrdua, String gela, double aurrekontua, EkitaldiMota ekitaldiMota, String usuario, List<String> gertaerak) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.hasierakoDataOrdua = hasierakoDataOrdua;
        this.bukaerakoDataOrdua = bukaerakoDataOrdua;
        this.gela = gela;
        this.aurrekontua = aurrekontua;
        this.ekitaldiMota = ekitaldiMota;
        this.usuario = usuario;
        this.gerataerak = gertaerak;
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
                ", gela='" + gela + '\'' +
                ", aurrekontua=" + aurrekontua +
                ", ekitaldiMota=" + ekitaldiMota +
                ", usuario='" + usuario + '\'' +
                ", sucesos=" + gerataerak +
                '}';
    }


}
