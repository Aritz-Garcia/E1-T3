package com.e1t3.onplan;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.databinding.ActivityEkitaldiBinding;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EkitaldiActivity extends AppCompatActivity {

    //Layout Android elementuak
    private ActivityEkitaldiBinding binding;
    private LinearLayout linearLayout;

    // Datubaserako objektuak
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Ekitaldia ekitaldia;
    private DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();

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
        getSupportActionBar().setSubtitle("sairam");
//        FloatingActionButton fab = binding.fab;
        linearLayout = binding.getRoot().findViewById(R.id.linearLayout);

        setUp();

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setUp(){
        this.ekitaldia.setUpGertaerak(this.linearLayout);
    }

}