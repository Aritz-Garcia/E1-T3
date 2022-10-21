package com.e1t3.onplan;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import com.e1t3.onplan.model.Ekitaldia;

public class EkitaldiInprimakiaMota extends AppCompatActivity implements View.OnClickListener {
=======
import com.e1t3.onplan.shared.EkitaldiMota;

import java.util.ArrayList;
import java.util.List;

public class EkitaldiInprimakiaMota extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
>>>>>>> 669b7e2cd0542b6dd97530933b13353e0ffff799

    private Button btnVolverAtrasTE, btnCrearEvento;
    private ListView lvEkitaldiMota;
    private List<Spanned> llist = new ArrayList<>();
    private ArrayAdapter<Spanned> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_tipo_de_evento);

        btnVolverAtrasTE = findViewById(R.id.btnVolverAtrasET);
        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnVolverAtrasTE.setOnClickListener(this);
        btnCrearEvento.setOnClickListener(this);

        lvEkitaldiMota = findViewById(R.id.lvEkitaldiMota);
        lvEkitaldiMota.setOnItemClickListener(this);

        EkitaldiMota();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnVolverAtrasTE.getId()) {
            this.finish();
        } else if (view.getId() == btnCrearEvento.getId()) {
            //crear evento
            ekitaldiaSortu();
            this.finish();
        }
    }

<<<<<<< HEAD
    public void ekitaldiaSortu(){
        Ekitaldia ekitaldia;

=======
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
        Toast.makeText(this, llist.get(posizioa), Toast.LENGTH_SHORT).show();
>>>>>>> 669b7e2cd0542b6dd97530933b13353e0ffff799
    }
}