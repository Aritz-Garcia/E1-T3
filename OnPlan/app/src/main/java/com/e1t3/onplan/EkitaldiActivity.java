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
import com.e1t3.onplan.shared.Values;
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
        CollectionReference ekitaldiak = db.collection(Values.EKITALDIAK);

        Map<String, Object> ekitaldiTest = new HashMap<>();
        ekitaldiTest.put(Values.EKITALDIAK_IZENA, "TEST APP PUT IZENA");
        ekitaldiTest.put(Values.EKITALDIAK_DESKRIBAPENA, "APP PUT DESKRIBAPENA");
        ekitaldiTest.put(Values.EKITALDIAK_HASIERAKO_DATA_ORDUA, Timestamp.now());
        ekitaldiTest.put(Values.EKITALDIAK_BUKAERAKO_DATA_ORDUA, Timestamp.now());
        ekitaldiTest.put(Values.EKITALDIAK_GELA, "TEST APP PUT GELA");
        ekitaldiTest.put(Values.EKITALDIAK_AURREKONTUA, 10.0);
        ekitaldiTest.put(Values.EKITALDIAK_EKITALDI_MOTA, EkitaldiMota.BESTE_MOTA);
        ekitaldiTest.put(Values.EKITALDIAK_ERABILTZAILEA, "TEST APP PUT ERABILTZAILEA");
        List<String> gertaerak = Arrays.asList("TEST APP PUT GERTAERA 1", "TEST APP PUT GERTAERA 2");
        ekitaldiTest.put(Values.EKITALDIAK_GERTAERAK, gertaerak);
        ekitaldiak.document().set(ekitaldiTest);
        //TODO add event modification and deletion etc
    }

    // This method gets the event from the database given an id

    public void getEvent(String id) {
        db.collection(Values.EKITALDIAK)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ekitaldia = new Ekitaldia(document);
                        } else {
                        }
                    }
                });
    }

}