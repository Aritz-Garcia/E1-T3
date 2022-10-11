package com.example.calendario2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendario2.model.Ekitaldia;
import com.example.calendario2.shared.EkitaldiMota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineaDeTiempoEvento extends AppCompatActivity {
    // This is the activity that shows the timeline of the event

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Ekitaldia ekitaldia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_de_tiempo_evento);
        this.getEvent("test");
        this.setUp();
    }

    //visualize the event in layout
    private void setUp() {

    }

    private void putEvent() {
        CollectionReference cities = db.collection("ekitaldiak");

        Map<String, Object> ekitraldiTest = new HashMap<>();
        ekitraldiTest.put("izena", "TEST APP PUT IZENA");
        ekitraldiTest.put("deskribapena", "TEST APP PUT DESKRIBAPENA");
        ekitraldiTest.put("hasierakoDataOrdua", Timestamp.now());
        ekitraldiTest.put("bukaerakoDataOrdua", Timestamp.now());
        ekitraldiTest.put("gela", "TEST APP PUT GELA");
        ekitraldiTest.put("aurrekontua", 10.0);
        ekitraldiTest.put("ekitaldiMota", "TEST APP PUT EKITALDI MOTA");
        ekitraldiTest.put("erabiltzailea", "TEST APP PUT ERABILTZAILEA");
        List<String> gertaerak = Arrays.asList("TEST APP PUT GERTAERA 1", "TEST APP PUT GERTAERA 2");
        ekitraldiTest.put("gerataerak", gertaerak);
        cities.document().set(ekitraldiTest);
        //TODO add event modification and deletion etc
    }

    // This method gets the event from the database given an id

    public void getEvent(String id) {
        db.collection("ekitaldiak")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                                Map<String, Object> ekitaldiaRead = document.getData();
                                String izena = ekitaldiaRead.get("izena").toString();
                                String deskribapena = ekitaldiaRead.get("deskribapena").toString();
                                Timestamp hasierakoDataOrdua = (Timestamp) ekitaldiaRead.get("hasierakoDataOrdua");
                                Timestamp bukaerakoDataOrdua = (Timestamp) ekitaldiaRead.get("bukaerakoDataOrdua");
                                String gela = ekitaldiaRead.get("gela").toString();
                                double aurrekontua = (double) ekitaldiaRead.get("aurrekontua");
                                EkitaldiMota ekitaldiMota = EkitaldiMota.valueOf(ekitaldiaRead.get("ekitaldiMota").toString());
                                String usuario = ekitaldiaRead.get("erabiltzailea").toString();
                                List<String> gerataerak = (List<String>) ekitaldiaRead.get("gerataerak");

                                ekitaldia = new Ekitaldia(
                                        document.getId(),
                                        izena,
                                        deskribapena,
                                        hasierakoDataOrdua,
                                        bukaerakoDataOrdua,
                                        gela,
                                        aurrekontua,
                                        ekitaldiMota,
                                        usuario,
                                        gerataerak);
                        } else {
                        }
                    }
                });
    }



}
