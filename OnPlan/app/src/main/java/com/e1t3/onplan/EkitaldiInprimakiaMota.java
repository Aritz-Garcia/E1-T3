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
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Gela;
import com.e1t3.onplan.shared.EkitaldiMota;
import com.e1t3.onplan.shared.Values;
import com.e1t3.onplan.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class EkitaldiInprimakiaMota extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnVolverAtrasTE;
    private ListView lvEkitaldiMota;
    private List<Spanned> llist = new ArrayList<>();
    private ArrayAdapter<Spanned> arrayAdapter;
    private SharedPreferences ekitaldia;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_tipo_de_evento);

        btnVolverAtrasTE = findViewById(R.id.btnVolverAtrasET);
        btnVolverAtrasTE.setOnClickListener(this);

        lvEkitaldiMota = findViewById(R.id.lvEkitaldiMota);
        lvEkitaldiMota.setOnItemClickListener(this);

        ekitaldia = getSharedPreferences("datuak", Context.MODE_PRIVATE);
        editor = ekitaldia.edit();

        EkitaldiMota();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnVolverAtrasTE.getId()) {
            this.finish();
        }
    }

    public void EkitaldiMota() {
        llist.add(Html.fromHtml(EkitaldiMota.OSPAKIZUNA.toString()));
        llist.add(Html.fromHtml(EkitaldiMota.IKUSKIZUNA.toString()));
        llist.add(Html.fromHtml(EkitaldiMota.HITZALDIA.toString()));
        llist.add(Html.fromHtml(EkitaldiMota.ERAKUSKETA.toString()));
        llist.add(Html.fromHtml(EkitaldiMota.BESTE_MOTA.toString()));
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, llist);
        lvEkitaldiMota.setAdapter(arrayAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posizioa, long l) {
        String ekitaldiMota = llist.get(posizioa).toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zihur zaude " + ekitaldiMota + " ekitaldi mota aukeratu nahi duzula")
                .setTitle("Ohartarazpena")
                .setPositiveButton("Onartu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString(Values.EKITALDIAK_EKITALDI_MOTA, ekitaldiMota);
                        editor.commit();
                        Intent intent = new Intent(EkitaldiInprimakiaMota.this, HomeFragment.class);
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
}