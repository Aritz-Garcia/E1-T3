package com.e1t3.onplan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.dao.DAOGertaerak;
import com.e1t3.onplan.databinding.ActivityEkitaldiaEditatuBinding;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.e1t3.onplan.ui.dialog.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EkitaldiaEditatu extends AppCompatActivity {
    //Layout Android elementuak
    private ActivityEkitaldiaEditatuBinding binding;
    private LinearLayout linearLayout;
    private Button btnGorde;

    // Datubaserako objektuak
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();

    private Ekitaldia ekitaldia;

    private EditText izenaText;
    private EditText deskribapenaText;
    private EditText dataText;
    private EditText orduaText;

    private String izena;
    private String deskribapena;
    private String data;
    private String ordua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkitaldiaEditatuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //get String array from enum
        String[] motak = new String[EkitaldiMota.values().length];
        for (int i = 0; i < EkitaldiMota.values().length; i++) {
            motak[i] = EkitaldiMota.values()[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_dropdown_item, motak);
        //set the spinners adapter to the previously created one.
        //FloatingActionButton fab = binding.fab;
        linearLayout = binding.getRoot().findViewById(R.id.linearLayout);
        String id = getIntent().getExtras().getString("id");
        setUp(id);

        btnGorde = findViewById(R.id.btnGorde);
        btnGorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekitaldia.setIzena(binding.ekitaldiIzena.getText().toString());
                ekitaldia.setDeskribapena(binding.ekitaldiDeskribapena.getText().toString());
                daoEkitaldiak.gehituEdoEguneratuEkitaldia(ekitaldia);
                Intent intent = new Intent(EkitaldiaEditatu.this, EkitaldiActivity.class);
                intent.putExtra("id", ekitaldia.getId());
                startActivity(intent);
            }
        });

    }

    public void setUp(String id){
        db.collection(Values.EKITALDIAK)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ekitaldia = new Ekitaldia(document);

                            TextView dataH = binding.getRoot().findViewById(R.id.ekitaldiDataHasiera);
                            dataH.setText(ekitaldia.getHasierakoDataOrdua());
                            TextView dataB = binding.getRoot().findViewById(R.id.ekitaldiDataBukaera);
                            dataB.setText(ekitaldia.getBukaerakoDataOrdua());

                            TextView izena = binding.getRoot().findViewById(R.id.ekitaldiIzena);
                            izena.setText(ekitaldia.getIzena());

                            TextView deskribapena = binding.getRoot().findViewById(R.id.ekitaldiDeskribapena);
                            deskribapena.setText(ekitaldia.getDeskribapena());

                            TextView aurrekontua = binding.getRoot().findViewById(R.id.ekitaldiAurrekontua);
                            aurrekontua.setText(String.format("%.2f",ekitaldia.getAurrekontua()));

                            TextView gela = binding.getRoot().findViewById(R.id.ekitaldiGela);
                            gela.setText(ekitaldia.getGela());

                            List<String> ids = (List<String>) document.get(Values.EKITALDIAK_GERTAERAK);
                            lortuGertaerakIdzEdit(ids, linearLayout, ekitaldia);


                        } else { }
                    }
                });
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

                                        izenaText = (EditText) viewInflated.findViewById(R.id.popupIzena);
                                        deskribapenaText = (EditText) viewInflated.findViewById(R.id.popupDeskribapena);
                                        dataText = (EditText) viewInflated.findViewById(R.id.popupData);
                                        dataText.setText("");

                                        dataText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showDatePickerDialogFin();
                                            }
                                        });
                                        orduaText = (EditText) viewInflated.findViewById(R.id.popupOrdua);
                                        String ordua = "00:00";
                                        orduaText.setText(ordua);
                                        orduaText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showTimePickerDialog();
                                            }
                                        });

                                        izena = izenaText.getText().toString();
                                        deskribapena = deskribapenaText.getText().toString();
                                        data = dataText.getText().toString();
                                        ordua = orduaText.getText().toString();

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
                                                        date = sdf.parse(dataText.getText().toString() + " " + orduaText.getText().toString());
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Gertaera g = new Gertaera(ekitaldia.getId() + "G" +ekitaldia.getGertaerak().size()+1 ,izenaText.getText().toString(), deskribapenaText.getText().toString(), false, new Timestamp(date));
                                                    DAOGertaerak daoGertaerak = new DAOGertaerak();
                                                    ekitaldia.gehituGertaera(g);
                                                    daoGertaerak.gehituEdoEguneratuGertaera(g);
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

                            } else {
                            }
                        }
                    });
        }

    }

    private void showDatePickerDialogFin() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
                dataText.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String denbora = dosDigitos(hourOfDay) + ":" + dosDigitos(minutes);
                orduaText.setText(denbora);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }

        private boolean validate(){
        if (this.izenaText.getText().toString().isEmpty()) return false;
        if (this.dataText.getText().toString().isEmpty()) return false;
        if (this.orduaText.getText().toString().isEmpty()) return false;
        return egunaKonprobatu();
    }

    private String dosDigitos(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    //check if date and time is correct
    private boolean egunaKonprobatu() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = sdf.parse(dataText.getText().toString() + " " + orduaText.getText().toString());
        } catch (ParseException e) {
            return false;
        }
        return ekitaldia.getDataTarteanDago(date);
    }

}
