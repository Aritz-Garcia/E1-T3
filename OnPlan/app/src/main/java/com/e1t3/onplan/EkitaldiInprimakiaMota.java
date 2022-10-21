package com.e1t3.onplan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.model.Ekitaldia;

public class EkitaldiInprimakiaMota extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolverAtrasTE, btnCrearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_tipo_de_evento);

        btnVolverAtrasTE = findViewById(R.id.btnVolverAtrasET);
        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnVolverAtrasTE.setOnClickListener(this);
        btnCrearEvento.setOnClickListener(this);

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

    public void ekitaldiaSortu(){
        Ekitaldia ekitaldia;

    }
}