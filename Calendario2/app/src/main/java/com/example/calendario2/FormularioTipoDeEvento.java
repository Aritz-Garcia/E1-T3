package com.example.calendario2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormularioTipoDeEvento extends AppCompatActivity implements View.OnClickListener {

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
            this.finish();
        }
    }
}