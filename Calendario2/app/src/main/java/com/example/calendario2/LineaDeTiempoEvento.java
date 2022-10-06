package com.example.calendario2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendario2.model.Ekitaldia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LineaDeTiempoEvento extends AppCompatActivity {

    // This is the activity that shows the timeline of the event

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Ekitaldia ekitaldia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_de_tiempo_evento);
        this.getEvent("0zBKPKIuH294LkU4u3KQ");
        Log.i("Ekitaldia :::", ekitaldia.toString());
    }

    // This method gets the event from the database
    public void getEvent(String id) {
        db.collection("reservas").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                if (document.getId().equals(id)) {
                                    ekitaldia = document.toObject(Ekitaldia.class);
                                }
                            }
                        }
                    }
                });
    }



}
