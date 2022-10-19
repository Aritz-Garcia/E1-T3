package com.e1t3.onplan.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.e1t3.onplan.R;
import com.e1t3.onplan.model.Erabiltzailea;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOErabiltzaileak {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOErabiltzaileak() { }

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



    public boolean gehituEdoEguneratuErabiltzailea(Erabiltzailea erabiltzailea) {
        Map<String, Object> erabiltzaileDoc = erabiltzailea.getDocument();
        db.collection(Values.ERABILTZAILEAK)
                .document(erabiltzailea.getId())
                .set(erabiltzaileDoc);
        return true;
    }

    public boolean ezabatuErabiltzailea(Erabiltzailea erabiltzailea) {
        db.collection(Values.ERABILTZAILEAK)
                .document(erabiltzailea.getId())
                .delete();
        return true;
    }

    /**public String lortuIzena(String email) {
        db.collection(Values.ERABILTZAILEAK)
                .whereEqualTo(Values.ERABILTZAILEAK_ENPRESA_DA, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString(Values.ERABILTZAILEAK_EMAIL).equals(email)) {
                                    Log.d(TAG, document.getId() + " => " + document.getString(Values.ERABILTZAILEAK_IZENA));
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }*/

}
