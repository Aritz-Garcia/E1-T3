package com.e1t3.onplan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.model.Gela;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EkitaldiInprimakiaGelak extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnVolverAtras;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListView lvGelakForm;
    private List<Spanned> llist = new ArrayList<>();
    private ArrayAdapter<Spanned> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_eventos_salas);

        btnVolverAtras = findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(this);

        lvGelakForm = findViewById(R.id.lvGelakForm);
        lvGelakForm.setOnItemClickListener(this);

        listaGelak();


    }

    public void onClick(View view){
        if (view.getId() == btnVolverAtras.getId()) {
            this.finish();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void listaGelak() {
        db.collection(Values.GELAK)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela =  new Gela(document);
                                llist.add(Html.fromHtml(gela.toString()));
                                arrayAdapter = new ArrayAdapter<>(EkitaldiInprimakiaGelak.this, android.R.layout.simple_list_item_1, llist);
                                lvGelakForm.setAdapter(arrayAdapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posizioa, long l) {
        //Toast.makeText(this, llist.get(posizioa), Toast.LENGTH_SHORT).show();
        String proba = llist.get(posizioa).toString();
        String[] proba1 = proba.split("\n");
        String[] proba2 = proba1[1].split(": ");
        getIdGela(proba2[1]);
        Intent i = new Intent(this, EkitaldiInprimakiaMota.class);
        startActivity(i);
    }

    private void getIdGela(String gelaIzena) {
        db.collection(Values.GELAK)
                .whereEqualTo(Values.GELAK_IZENA, gelaIzena)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela =  new Gela(document);
                                gela.getId();
                                Intent i = new Intent(EkitaldiInprimakiaGelak.this, EkitaldiInprimakiaMota.class);
                                startActivity(i);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}