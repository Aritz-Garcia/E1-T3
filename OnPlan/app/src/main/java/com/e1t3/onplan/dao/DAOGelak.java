package com.e1t3.onplan.dao;

import androidx.annotation.NonNull;

import com.e1t3.onplan.model.Gela;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DAOGelak {

    private static FirebaseFirestore db;

    private DAOGelak() {
        db = FirebaseFirestore.getInstance();
    }

    public List<Gela> lortuGelak() {
        List<Gela> gelak= new ArrayList<>();
        db.collection(Values.GELAK)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela = new Gela(document);
                                gelak.add(gela);
                            }
                        } else {
                        }
                    }
                });
        return gelak;
    }

}
