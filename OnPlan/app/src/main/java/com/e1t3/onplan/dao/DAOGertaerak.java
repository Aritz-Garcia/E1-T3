package com.e1t3.onplan.dao;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public List<Gertaera> lortuGertaerakIdz(List<String> ids, LinearLayout linearLayout) {
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
                                LinearLayout linearLayoutGertaera = new LinearLayout(linearLayout.getContext());
                                linearLayoutGertaera.setOrientation(LinearLayout.HORIZONTAL);
                                linearLayoutGertaera.setGravity(Gravity.CENTER_VERTICAL);
                                linearLayoutGertaera.setPadding(16, 16, 16, 16);

                                TextView gertaeraOrdua = new TextView(linearLayout.getContext());
                                gertaeraOrdua.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                                gertaeraOrdua.setTextColor(Color.parseColor("#004f53"));

                                LinearLayout checkLayout = new LinearLayout(linearLayout.getContext());

                                CheckBox gertaeraEginda = new CheckBox(linearLayout.getContext());
                                if (gertaera.eginDa()) {
                                    gertaeraEginda.setChecked(true);
                                    gertaeraEginda.setClickable(false);
                                }
                                gertaeraEginda.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                    if (isChecked) {
                                        gertaera.setEginda();
                                        gertaeraEginda.setClickable(false);
                                        gehituEdoEguneratuGertaera(gertaera);

                                    }
                                });

                                LinearLayout textLayout = new LinearLayout(linearLayout.getContext());

                                TextView gertaeraIzena = new TextView(linearLayout.getContext());
                                gertaeraIzena.setTextSize(1,20);
                                gertaeraIzena.setTextColor(Color.parseColor("#001e20"));

                                TextView gertaeraDeskribapena = new TextView(linearLayout.getContext());
                                gertaeraDeskribapena.setTextColor(Color.parseColor("#004f53"));



                                linearLayoutGertaera.addView(gertaeraOrdua);
                                checkLayout.setOrientation(LinearLayout.VERTICAL);
                                linearLayoutGertaera.addView(checkLayout);
                                checkLayout.addView(gertaeraEginda);
                                textLayout.setOrientation(LinearLayout.VERTICAL);
                                linearLayoutGertaera.addView(textLayout);
                                textLayout.addView(gertaeraIzena);
                                textLayout.addView(gertaeraDeskribapena);

                                gertaeraOrdua.setText(gertaera.getOrdua());
                                gertaeraEginda.setChecked(gertaera.eginDa());
                                gertaeraIzena.setText(gertaera.getIzena());
                                gertaeraDeskribapena.setText(gertaera.getDeskribapena());

                                linearLayout.addView(linearLayoutGertaera);
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

    public boolean gehituEdoEguneratuGertaera(Gertaera gertaera) {
            Map<String, Object> ekitaldiDoc = gertaera.getDocument();
            db.collection(Values.GERTAERAK)
                    .document(gertaera.getId())
                    .set(ekitaldiDoc);
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
