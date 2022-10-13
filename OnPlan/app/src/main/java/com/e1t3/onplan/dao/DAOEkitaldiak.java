package com.e1t3.onplan.dao;

import androidx.annotation.NonNull;

import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DAOEkitaldiak {
    private static DAOEkitaldiak instance = null;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOEkitaldiak() {
    }
    public static DAOEkitaldiak getInstance() {
        if (instance == null) {
            instance = new DAOEkitaldiak();
        }
        return instance;
    }

    public Ekitaldia getEkitaldiaById(String id){
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

    

}
