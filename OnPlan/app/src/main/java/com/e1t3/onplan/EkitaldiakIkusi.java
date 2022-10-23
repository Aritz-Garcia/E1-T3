package com.e1t3.onplan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Erabiltzailea;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EkitaldiakIkusi extends AppCompatActivity implements View.OnClickListener {

    private Button btnLineaTiempo;
    private FirebaseUser user;
    private String email;
    private ListView lista;
    private ArrayList<Ekitaldia> ekitaldiak =new ArrayList<Ekitaldia>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eventos);
        btnLineaTiempo = findViewById(R.id.btnLineaTimepo);
        btnLineaTiempo.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
        lista = findViewById(R.id.lista);
        erabiltzaileId();
        ArrayAdapter<Ekitaldia> adapter = new ArrayAdapter<Ekitaldia>(this,R.layout.activity_view_eventos, ekitaldiak);
        lista.setAdapter(adapter);
    }

    public void erabiltzaileId(){
        db.collection(Values.ERABILTZAILEAK)
                .whereEqualTo(Values.ERABILTZAILEAK_EMAIL, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Erabiltzailea erabiltzailea = new Erabiltzailea(document);
                                db.collection(Values.EKITALDIAK)
                                        .whereEqualTo(Values.EKITALDIAK_ERABILTZAILEA,erabiltzailea.getId().toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Ekitaldia ekitaldia = new Ekitaldia(document);
                                                        ekitaldiak.add(ekitaldia);
                                                    }

                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
    @Override
    public void onClick(View view) {
        if (view.getId() == btnLineaTiempo.getId()) {
            Intent i = new Intent(this, EkitaldiActivity.class);
            startActivity(i);
        }
    }
}