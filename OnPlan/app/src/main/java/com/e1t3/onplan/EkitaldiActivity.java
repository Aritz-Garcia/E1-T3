package com.e1t3.onplan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e1t3.onplan.databinding.ActivityEkitaldiBinding;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EkitaldiActivity extends AppCompatActivity {

    private ActivityEkitaldiBinding binding;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Ekitaldia ekitaldia;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEkitaldiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
//        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Ekitaldia");
        textView = findViewById(R.id.text);
        getSupportActionBar().setSubtitle("sairam");
        textView.setText(" subtittle is sairam");

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //visualize the event in layout
    private void setUp() {

    }

    private void putEvent() {
        CollectionReference ekitaldiak = db.collection("ekitaldiak");

        Map<String, Object> ekitraldiTest = new HashMap<>();
        ekitraldiTest.put("izena", "TEST APP PUT IZENA");
        ekitraldiTest.put("deskribapena", "TEST APP PUT DESKRIBAPENA");
        ekitraldiTest.put("hasierakoDataOrdua", Timestamp.now());
        ekitraldiTest.put("bukaerakoDataOrdua", Timestamp.now());
        ekitraldiTest.put("gela", "TEST APP PUT GELA");
        ekitraldiTest.put("aurrekontua", 10.0);
        ekitraldiTest.put("ekitaldiMota", EkitaldiMota.BESTE_MOTA);
        ekitraldiTest.put("erabiltzailea", "TEST APP PUT ERABILTZAILEA");
        List<String> gertaerak = Arrays.asList("TEST APP PUT GERTAERA 1", "TEST APP PUT GERTAERA 2");
        ekitraldiTest.put("gerataerak", gertaerak);
        ekitaldiak.document().set(ekitraldiTest);
        //TODO add event modification and deletion etc
    }

    // This method gets the event from the database given an id

    public void getEvent(String id) {
        db.collection("ekitaldiak")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> ekitaldiaRead = document.getData();
                            String izena = ekitaldiaRead.get("izena").toString();
                            String deskribapena = ekitaldiaRead.get("deskribapena").toString();
                            Timestamp hasierakoDataOrdua = (Timestamp) ekitaldiaRead.get("hasierakoDataOrdua");
                            Timestamp bukaerakoDataOrdua = (Timestamp) ekitaldiaRead.get("bukaerakoDataOrdua");
                            String gela = ekitaldiaRead.get("gela").toString();
                            double aurrekontua = (double) ekitaldiaRead.get("aurrekontua");
                            EkitaldiMota ekitaldiMota = EkitaldiMota.valueOf(ekitaldiaRead.get("ekitaldiMota").toString());
                            String usuario = ekitaldiaRead.get("erabiltzailea").toString();
                            List<String> gerataerak = (List<String>) ekitaldiaRead.get("gerataerak");

                            ekitaldia = new Ekitaldia(
                                    document.getId(),
                                    izena,
                                    deskribapena,
                                    hasierakoDataOrdua,
                                    bukaerakoDataOrdua,
                                    gela,
                                    aurrekontua,
                                    ekitaldiMota,
                                    usuario,
                                    gerataerak);
                        } else {
                        }
                    }
                });
    }

}