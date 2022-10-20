package com.e1t3.onplan.model;

import com.e1t3.onplan.shared.Values;
import com.google.firebase.firestore.DocumentSnapshot;

public class Gela {
    private String id;
    private String izena;
    private double prezioa;
    private int edukiera;
    private String gehigarriak;

    public Gela(DocumentSnapshot document) {
        this.id             = document.getId();
        this.izena          = document.getString(Values.GELAK_IZENA);
        this.edukiera       = document.getLong(Values.GELAK_EDUKIERA).intValue(); //ezin da integer bat eskuratu
        this.prezioa        = document.getDouble(Values.GELAK_PREZIOA);
        this.gehigarriak    = document.getString(Values.GELAK_GEHIGARRIAK);
    }

    public String getIzena() { return izena; }
    public int getEdukiera() { return edukiera; }
    public Double getPrezioa() { return prezioa; }
    public String getGehigarriak() { return gehigarriak; }

}
