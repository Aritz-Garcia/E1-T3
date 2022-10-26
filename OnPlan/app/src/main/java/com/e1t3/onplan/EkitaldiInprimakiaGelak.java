package com.e1t3.onplan;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatDelegate;

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
    private SharedPreferences ekitaldia;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_eventos_salas);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        setDayNight();

        btnVolverAtras = findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(this);

        lvGelakForm = findViewById(R.id.lvGelakForm);
        lvGelakForm.setOnItemClickListener(this);

        ekitaldia = getSharedPreferences("datuak", Context.MODE_PRIVATE);
        editor = ekitaldia.edit();
        arrayAdapter = new ArrayAdapter<>(EkitaldiInprimakiaGelak.this, android.R.layout.simple_list_item_1, llist);
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
                .whereLessThanOrEqualTo(Values.GELAK_PREZIOA, ekitaldia.getFloat(Values.EKITALDIAK_AURREKONTUA, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela =  new Gela(document);
                                if (gela.getEdukiera() >= ekitaldia.getInt("edukiera", 0)) {
                                    llist.add(Html.fromHtml(gela.toString()));
                                }
                            }
                            if (llist.size() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EkitaldiInprimakiaGelak.this);
                                builder.setMessage("Ez dago gelarik aurrekontu edo/eta edukiera horretarako. Berriro Sailatu")
                                        .setTitle("Error")
                                        .setPositiveButton("Datuak berriro sartu", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Irten", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(EkitaldiInprimakiaGelak.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                arrayAdapter.notifyDataSetChanged();
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
        String datuGuztaik = llist.get(posizioa).toString();
        String[] izenaLortu = datuGuztaik.split("\n");
        String[] izena = izenaLortu[1].split(": ");
        getIdGela(izena[1]);
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
                                String gelaId = gela.getId();
                                AlertDialog.Builder builder = new AlertDialog.Builder(EkitaldiInprimakiaGelak.this);
                                builder.setMessage("Zihur zaude " + gelaIzena + " gela aukeratu nahi duzula")
                                        .setTitle("Ohartarazpena")
                                        .setPositiveButton("Onartu", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                editor.putString(Values.EKITALDIAK_GELA, gelaId);
                                                editor.commit();
                                                Intent intent = new Intent(EkitaldiInprimakiaGelak.this, EkitaldiInprimakiaMota.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Ezeztatu", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void setDayNight() {
        boolean oscuro = sharedPreferences.getBoolean("oscuro", false);
        if (oscuro) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}