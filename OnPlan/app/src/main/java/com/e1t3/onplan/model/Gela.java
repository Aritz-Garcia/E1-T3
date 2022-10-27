package com.e1t3.onplan.model;

import android.content.Context;

import com.e1t3.onplan.R;
import com.e1t3.onplan.shared.Values;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DecimalFormat;

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

    /**
     * Metodo honek gelaren izena itzultzen du.
     * @return String
     */

    public String getIzena() { return izena; }

    /**
     * Metodo honek gelaren id-a itzultzen du.
     * @return String
     */

    public String getId() { return id; }

    /**
     * Metodo honek gelaren edukiera itzultzen du.
     * @return int
     */

    public int getEdukiera() { return edukiera; }

    /**
     * Metodo honek gelaren datuak html formatu batean bueltatzen ditu.
     * @return String
     */

    public String toString() {

        return "<br/><b style='font-size:30px'>" + R.string.gela_izena + "</b> " + izena + "<br/><b>" + R.string.gela_edukiera + "</b>" + edukiera + "<br/><b>" + R.string.gela_prezioa + "</b>" + getTwoDecimals(prezioa) + "€<br/><b>" + R.string.gela_gehigarria + "</b>" + gehigarriak + "<br/>";
    }

    /**
     * Metodo honek zenbaki bat bi desimal baino gehiago ez duen formatuan itzultzen du.
     * @param value double
     * @return String
     */

    private static String getTwoDecimals(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

}
