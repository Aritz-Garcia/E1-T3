package com.e1t3.onplan.dao;

import androidx.annotation.NonNull;

import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOEkitaldiak {

    private static FirebaseFirestore db;

    public DAOEkitaldiak() {
        db = FirebaseFirestore.getInstance();
    }

    public Ekitaldia lortuEkitaldiaIdz(String id){
        final Ekitaldia[] ekitaldia = new Ekitaldia[1]; // array bat erabili behar da, bestela ezin da balioa aldatu
        db.collection(Values.EKITALDIAK)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ekitaldia[0] = new Ekitaldia(document);
                        } else {
                        }
                    }
                });
        return ekitaldia[0];
    }

    public List<Ekitaldia> lortuEkitaldiak(){
        List<Ekitaldia> ekitaldiak= new ArrayList<>();
        db.collection(Values.EKITALDIAK)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ekitaldia ekitaldia = new Ekitaldia(document);
                                ekitaldiak.add(ekitaldia);
                            }
                        } else {
                        }
                    }
                });
        return ekitaldiak;
    }

    public boolean gehituEdoEguneratuEkitaldia(Ekitaldia ekitaldia){
        Map<String, Object> ekitaldiDoc = ekitaldia.getDocument();
        db.collection(Values.EKITALDIAK)
                .document(ekitaldia.getId())
                .set(ekitaldiDoc);
        return true;
    }

    public boolean ezabatuEkitaldia(Ekitaldia ekitaldia){
        db.collection(Values.EKITALDIAK)
                .document(ekitaldia.getId())
                .delete();
        return true;
    }

}
