package com.e1t3.onplan.model;

import com.e1t3.onplan.shared.Values;
import com.google.firebase.firestore.DocumentSnapshot;

public class Erabiltzailea {
    private String id;
    private String izena;
    private String abizena;
    private String email;
    private String nanIfz;
    private String telefonoa;
    private boolean enpresaDa;

    public Erabiltzailea(DocumentSnapshot document) {
        this.id             = document.getId();
        this.izena          = document.getString(Values.ERABILTZAILEAK_IZENA);
        this.abizena        = document.getString(Values.ERABILTZAILEAK_ABIZENA);
        this.email          = document.getString(Values.ERABILTZAILEAK_EMAIL);
        this.nanIfz         = document.getString(Values.ERABILTZAILEAK_NAN_IFZ);
        this.telefonoa      = document.getString(Values.ERABILTZAILEAK_TELEFONOA);
        this.enpresaDa      = document.getBoolean(Values.ERABILTZAILEAK_ENPRESA_DA);
    }
}
