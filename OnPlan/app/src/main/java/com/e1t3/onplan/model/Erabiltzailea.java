package com.e1t3.onplan.model;

import com.e1t3.onplan.shared.Values;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class Erabiltzailea {
    private String id;
    private String izena;
    private String abizena;
    private String email;
    private String nanIfz;
    private String telefonoa;
    private boolean enpresaDa;
    private boolean admin;

    public Erabiltzailea(DocumentSnapshot document) {
        this.id             = document.getId();
        this.izena          = document.getString(Values.ERABILTZAILEAK_IZENA);
        this.abizena        = document.getString(Values.ERABILTZAILEAK_ABIZENA);
        this.email          = document.getString(Values.ERABILTZAILEAK_EMAIL);
        this.nanIfz         = document.getString(Values.ERABILTZAILEAK_NAN_IFZ);
        this.telefonoa      = document.getString(Values.ERABILTZAILEAK_TELEFONOA);
        this.enpresaDa      = document.getBoolean(Values.ERABILTZAILEAK_ENPRESA_DA);
        this.admin          = document.getBoolean(Values.ERABILTZAILEAK_ADMIN);
    }

    //getters and setters
    public String getId() {
        return id;
    }
    public boolean getEnpresaDa() { return enpresaDa; }
    public String getIzena() { return izena; }
    public String getAbizena() { return abizena; }
    public String getEmail() { return email; }
    public String getNanIfz() { return nanIfz; }
    public String getTelefonoa() { return telefonoa; }

    public Map<String, Object> getDocument() {
        return Map.of(
                Values.ERABILTZAILEAK_IZENA, izena,
                Values.ERABILTZAILEAK_ABIZENA, abizena,
                Values.ERABILTZAILEAK_EMAIL, email,
                Values.ERABILTZAILEAK_NAN_IFZ, nanIfz,
                Values.ERABILTZAILEAK_TELEFONOA, telefonoa,
                Values.ERABILTZAILEAK_ENPRESA_DA, enpresaDa,
                Values.ERABILTZAILEAK_ADMIN, admin
        );
    }

    public boolean adminDa() {
        return admin;
    }
}
