package com.e1t3.onplan.model;

import com.e1t3.onplan.shared.Values;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.type.Date;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class Gertaera {
    private String id;
    private String izena;
    private String deskribapena;
    private boolean eginDa;
    private Timestamp ordua;

    public Gertaera(DocumentSnapshot document) {
        this.id             = document.getId();
        this.izena          = document.getString(Values.GERTAERAK_IZENA);
        this.deskribapena   = document.getString(Values.GERTAERAK_DESKRIBAPENA);
        this.eginDa         = document.getBoolean(Values.GERTAERAK_EGIN_DA);
        this.ordua          = document.getTimestamp(Values.GERTAERAK_ORDUA);
    }


    public Map<String, Object> getDocument() {
        return Map.of(
                Values.GERTAERAK_IZENA, izena,
                Values.GERTAERAK_DESKRIBAPENA, deskribapena,
                Values.GERTAERAK_EGIN_DA, eginDa,
                Values.GERTAERAK_ORDUA, ordua
        );
    }

    public String getId() {
        return id;
    }

    public String getOrdua() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return sdf.format(this.ordua.toDate());
    }

    public String getIzena() {
        return izena;
    }

    public boolean eginDa() {
        return this.eginDa;
    }

    public String getDeskribapena() {
        return deskribapena;
    }
}
