package com.e1t3.onplan.dao;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.print.PrintAttributes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.e1t3.onplan.R;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    public void lortuGertaerakIdzEdit(List<String> ids, LinearLayout linearLayout, Ekitaldia ekitaldia) {
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
                                }

                                gertaeraEginda.setClickable(false);

                                LinearLayout textLayout = new LinearLayout(linearLayout.getContext());

                                WindowManager mWinMgr = (WindowManager) linearLayout.getContext().getSystemService(Context.WINDOW_SERVICE);
                                int displayWidth = mWinMgr.getDefaultDisplay().getWidth();

                                textLayout.setMinimumWidth(3*displayWidth/5);

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

                                LinearLayout buttonLayout = new LinearLayout(linearLayout.getContext());
                                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

                                FloatingActionButton ezabatuBotoia = new FloatingActionButton(linearLayout.getContext());
                                ezabatuBotoia.setImageResource(R.drawable.gertaera_delete);
                                ezabatuBotoia.setCustomSize(100);
                                ezabatuBotoia.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FE4444")));

                                ezabatuBotoia.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        linearLayout.removeView(linearLayoutGertaera);
                                        DAOGertaerak daoGertaerak = new DAOGertaerak();
                                        daoGertaerak.ezabatuGertaeraIdz(gertaera.getId());
                                        ekitaldia.ezabatuGertaera(gertaera.getId());
                                        DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();
                                        daoEkitaldiak.gehituEdoEguneratuEkitaldia(ekitaldia);
                                    }
                                });

                                buttonLayout.addView(ezabatuBotoia);

                                linearLayoutGertaera.addView(buttonLayout);
                                linearLayout.addView(linearLayoutGertaera);
                            }
                            LinearLayout linearLayoutGertaera = new LinearLayout(linearLayout.getContext());
                            linearLayoutGertaera.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayoutGertaera.setPadding(16, 16, 16, 16);

                            FloatingActionButton gehituBotoia = new FloatingActionButton(linearLayout.getContext());
                            gehituBotoia.setImageResource(R.drawable.gertaera_add);
                            gehituBotoia.setCustomSize(100);

                            //TODO: Gehitu botoia onClickListener-a
                            gehituBotoia.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(linearLayout.getContext(), GertaeraSortuActivity.class);
                                    intent.putExtra("id", gertaera.getId());
                                    linearLayout.getContext().startActivity(intent);
                                }
                            });

                            linearLayoutGertaera.addView(gehituBotoia);
                            linearLayout.addView(linearLayoutGertaera);

                        } else {
                        }
                    }
                });
    }
}
