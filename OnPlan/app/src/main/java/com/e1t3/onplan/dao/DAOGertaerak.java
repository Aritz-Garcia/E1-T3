package com.e1t3.onplan.dao;

import androidx.annotation.NonNull;

import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DAOGertaerak {
    private static DAOGertaerak instance = null;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOGertaerak() {
    }
    public static DAOGertaerak getInstance() {
        if (instance == null) {
            instance = new DAOGertaerak();
        }
        return instance;
    }

    public List<Gertaera> getGertaerak(List<String> ids) {
        List<Gertaera> gertaerak= new ArrayList<>();

        db.collection(Values.GERTAERAK)
                .whereIn(FieldPath.documentId(), ids)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gertaera gertaera = new Gertaera(document);
                                gertaerak.add(gertaera);
                            }
                        } else {
                        }
                    }
                });
        return gertaerak;
    }

}
