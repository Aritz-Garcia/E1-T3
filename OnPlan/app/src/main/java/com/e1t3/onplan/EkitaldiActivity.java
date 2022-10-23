package com.e1t3.onplan;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.databinding.ActivityEkitaldiaBinding;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EkitaldiActivity extends AppCompatActivity {

    //Layout Android elementuak
    private ActivityEkitaldiaBinding binding;
    private LinearLayout linearLayout;

    // Datubaserako objektuak
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();

    private Ekitaldia ekitaldia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityEkitaldiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setSubtitle("oier1310@gmail.com");


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
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
                            Ekitaldia ekitaldia = new Ekitaldia(document);

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

                            ekitaldia.setUpGertaerak(document, linearLayout);


                        } else { }
                    }
                });
    }

}