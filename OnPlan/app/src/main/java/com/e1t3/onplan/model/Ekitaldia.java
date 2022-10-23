package com.e1t3.onplan.model;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e1t3.onplan.dao.DAOGertaerak;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
    private List<Gertaera> gerataerak;

    public Ekitaldia() {}

    public Ekitaldia(DocumentSnapshot document) {
        this.id                 = document.getId();
        this.izena              = document.getString(Values.EKITALDIAK_IZENA);
        this.deskribapena       = document.getString(Values.EKITALDIAK_DESKRIBAPENA);
        this.hasierakoDataOrdua = document.getTimestamp(Values.EKITALDIAK_HASIERAKO_DATA_ORDUA);
        this.bukaerakoDataOrdua = document.getTimestamp(Values.EKITALDIAK_BUKAERAKO_DATA_ORDUA);
        this.gela               = document.getString(Values.EKITALDIAK_GELA);
        this.aurrekontua        = document.getDouble(Values.EKITALDIAK_AURREKONTUA);
        this.ekitaldiMota       = EkitaldiMota.valueOf(document.getString(Values.EKITALDIAK_EKITALDI_MOTA));
        this.usuario            = document.getString(Values.EKITALDIAK_ERABILTZAILEA);
    }

    public Ekitaldia(String id, String izena, String deskribapena, Timestamp hasierakoDataOrdua, Timestamp bukaerakoDataOrdua, String gela, double aurrekontua, EkitaldiMota ekitaldiMota, String usuario, List<Gertaera> gerataerak) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.hasierakoDataOrdua = hasierakoDataOrdua;
        this.bukaerakoDataOrdua = bukaerakoDataOrdua;
        this.gela = gela;
        this.aurrekontua = aurrekontua;
        this.ekitaldiMota = ekitaldiMota;
        this.usuario = usuario;
        this.gerataerak = gerataerak;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public Map<String, Object> getDocument() {
        return Map.of(
                Values.EKITALDIAK_IZENA, izena,
                Values.EKITALDIAK_DESKRIBAPENA, deskribapena,
                Values.EKITALDIAK_HASIERAKO_DATA_ORDUA, hasierakoDataOrdua,
                Values.EKITALDIAK_BUKAERAKO_DATA_ORDUA, bukaerakoDataOrdua,
                Values.EKITALDIAK_GELA, gela,
                Values.EKITALDIAK_AURREKONTUA, aurrekontua,
                Values.EKITALDIAK_EKITALDI_MOTA, ekitaldiMota.toString(),
                Values.EKITALDIAK_ERABILTZAILEA, usuario,
                Values.EKITALDIAK_GERTAERAK, gerataerak
        );
    }

    public void setUpGertaerak(DocumentSnapshot document, LinearLayout linearLayout){
        DAOGertaerak daoGertaerak = new DAOGertaerak();
        List<String> ids = (List<String>) document.get(Values.EKITALDIAK_GERTAERAK);
        this.gerataerak         = daoGertaerak.lortuGertaerakIdz(ids, linearLayout);
    }

    public String getHasierakoDataOrdua() {
        return this.getDataString(this.hasierakoDataOrdua.getSeconds()*1000);
    }

    public String getBukaerakoDataOrdua() {
        return this.getDataString(this.bukaerakoDataOrdua.getSeconds()*1000);
    }
    private String getDataString(long miliseconds) {
        Date date = new Date(miliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC+2"));
        String formattedDate = sdf.format(date);
        return formattedDate; // Tuesday,November 1,2011 12:00,AM
    }

    public String getIzena() {
        return izena;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public String getGela() {
        return gela;
    }

    public double getAurrekontua() {
        return aurrekontua;
    }


    public void setIzena(String izena){
        this.izena = izena;
    }

    public void setAurrekontua(double aurrekontua){
        this.aurrekontua = aurrekontua;
    }
    public void setDeskribapena(String deskribapena){
        this.deskribapena = deskribapena;
    }
    public void setHasierakoDataOrdua(Timestamp hasierakoDataOrdua){
        this.hasierakoDataOrdua = hasierakoDataOrdua;
    }
    public String toString() {
        return  izena ;
    }

}
