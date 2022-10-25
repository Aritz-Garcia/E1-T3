package com.e1t3.onplan.dao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.print.PrintAttributes;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        if (ids.size() > 0) {
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


                        } else {
                        }
                    }
                });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(linearLayout.getContext());
                builder.setTitle(R.string.title_gertaera_popup);
                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.gehitu_gertera_popup, (ViewGroup) null, false);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                final EditText izena = (EditText) viewInflated.findViewById(R.id.popupIzena);
                final EditText deskribapena = (EditText) viewInflated.findViewById(R.id.popupDeskribapena);
                final EditText data = (EditText) viewInflated.findViewById(R.id.popupData);
                final EditText ordua = (EditText) viewInflated.findViewById(R.id.popupOrdua);

                // Set up the buttons
                builder.setPositiveButton(R.string.gehitu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(validate()) {
                            // get miliseconds from string date
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date date = null;
                            try {
                                date = sdf.parse(data.getText().toString() + " " + ordua.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Gertaera g = new Gertaera("TEST",izena.getText().toString(), deskribapena.getText().toString(), false, new Timestamp(date));
                            gehituEdoEguneratuGertaera(g);
                        }
                    }
                });
                builder.setNegativeButton(R.string.volver_atras, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        linearLayoutGertaera.addView(gehituBotoia);
        linearLayout.addView(linearLayoutGertaera);
    }
}
