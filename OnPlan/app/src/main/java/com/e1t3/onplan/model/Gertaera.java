package com.e1t3.onplan.model;

import com.e1t3.onplan.shared.Values;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class Gertaera {
    private String id;
    private String izena;
    private String deskribapena;
    private Timestamp ordua;

    public Gertaera(DocumentSnapshot document) {
        this.id             = document.getId();
        this.izena          = document.getString(Values.GERTAERAK_IZENA);
        this.deskribapena   = document.getString(Values.GERTAERAK_DESKRIBAPENA);
        this.ordua          = document.getTimestamp(Values.GERTAERAK_ORDUA);
    }
}
