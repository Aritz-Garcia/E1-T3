package com.e1t3.onplan.model;

import com.e1t3.onplan.dao.DAOGertaerak;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

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
    private List<Gertaera> gerataerak;

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
        this.gerataerak         = DAOGertaerak.getInstance().lortuGertaerakIdz((List<String>) document.get(Values.EKITALDIAK_GERTAERAK));
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
