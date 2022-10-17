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
import java.util.Map;

public class DAOGertaerak {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOGertaerak() {
        db = FirebaseFirestore.getInstance();
    }

    public List<Gertaera> lortuGertaerakIdz(List<String> ids) {
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

    public boolean gehituEdoEguneratuGertaerak(List<Gertaera> gertaerak) {
        for (Gertaera gertaera : gertaerak) {
            Map<String, Object> ekitaldiDoc = gertaera.getDocument();
            db.collection(Values.GERTAERAK)
                    .document(gertaera.getId())
                    .set(ekitaldiDoc);
        }
        return true;
    }

    public boolean ezabatuGertaeraIdz(String id) {
        db.collection(Values.GERTAERAK)
                .document(id)
                .delete();
        return true;
    }

    public boolean gertaerakIdzEzabatu(List<String> ids) {
        for (String id : ids) {
            db.collection(Values.GERTAERAK)
                    .document(id)
                    .delete();
        }
        return true;
    }

}
