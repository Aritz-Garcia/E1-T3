package com.e1t3.onplan.dao;

import androidx.annotation.NonNull;

import com.e1t3.onplan.model.Erabiltzailea;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOErabiltzaileak {
    private static DAOErabiltzaileak instance = null;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOErabiltzaileak() {
    }
    public static DAOErabiltzaileak getInstance() {
        if (instance == null) {
            instance = new DAOErabiltzaileak();
        }
        return instance;
    }

    public List<Erabiltzailea> lortuErabiltzaileak() {
        List<Erabiltzailea> erabiltzaileak= new ArrayList<>();
        db.collection(Values.ERABILTZAILEAK)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Erabiltzailea erabiltzailea = new Erabiltzailea(document);
                                erabiltzaileak.add(erabiltzailea);
                            }
                        } else {
                        }
                    }
                });
        return erabiltzaileak;
    }

    public Erabiltzailea lortuErabiltzaileaId(String id) {
        final Erabiltzailea[] erabiltzailea = {null};
        db.collection(Values.ERABILTZAILEAK)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                erabiltzailea[0] = new Erabiltzailea(document);
                            }
                        } else {
                        }
                    }
                });
        return erabiltzailea[0];
    }

    public boolean gehituEdoEguneratuErabiltzailea(Erabiltzailea erabiltzailea) {
        Map<String, Object> ekitaldiDoc = erabiltzailea.getDocument();
        db.collection(Values.ERABILTZAILEAK)
                .document(erabiltzailea.getId())
                .set(ekitaldiDoc);
        return true;
    }

    public boolean ezabatuErabiltzailea(Erabiltzailea erabiltzailea) {
        db.collection(Values.ERABILTZAILEAK)
                .document(erabiltzailea.getId())
                .delete();
        return true;
    }

}