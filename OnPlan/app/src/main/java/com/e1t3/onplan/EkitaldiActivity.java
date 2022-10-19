package com.e1t3.onplan;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.databinding.ActivityEkitaldiBinding;
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
    private ActivityEkitaldiBinding binding;
    private LinearLayout linearLayout;

    // Datubaserako objektuak
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();

    private Ekitaldia ekitaldia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ekitaldia = new Ekitaldia(
                "Id ekitaldia",
                "Ekitaldia 1",
                "Ekitaldiaren deskribapena",
                Timestamp.now(),
                Timestamp.now(),
                "Id gela",
                69,
                EkitaldiMota.BESTE_MOTA,
                "Id erabiltzailea",
                List.of(new Gertaera(
                        "Id gertaera",
                        "Gertaera 1",
                        "Gertaeraren deskribapena",
                        false,
                        Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        ),
                        new Gertaera(
                                "Id gertaera",
                                "Gertaera 1",
                                "Gertaeraren deskribapena",
                                false,
                                Timestamp.now()
                        )
                )
        );

        binding = ActivityEkitaldiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setSubtitle("oier1310@gmail.com");
//        FloatingActionButton fab = binding.fab;
        linearLayout = binding.getRoot().findViewById(R.id.linearLayout);

        setUp("1Dm6GUbJi5y6KEy6leyl");

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setUp(String id){
        db.collection(Values.EKITALDIAK)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Ekitaldia ekitaldia = new Ekitaldia(document);

                            TextView data = binding.getRoot().findViewById(R.id.ekitaldiData);
                            data.setText(ekitaldia.getDataOrdua().toString());

                            ekitaldia.setUpGertaerak(linearLayout);
                        } else { }
                    }
                });
    }

}