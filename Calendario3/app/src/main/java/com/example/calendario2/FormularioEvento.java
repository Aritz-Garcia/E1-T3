package com.example.calendario2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormularioEvento extends AppCompatActivity implements View.OnClickListener {

    private TextView descripcion, aforo, tipoEvento;
    private EditText nombreEvento, fecha;
    private Button volverAgenda, crearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_evento);

        nombreEvento = (EditText)findViewById(R.id.nombreEvento);
        descripcion = (TextView)findViewById(R.id.descripcion);
        aforo = (TextView)findViewById(R.id.aforo);
        fecha = (EditText)findViewById(R.id.fecha);
        tipoEvento = (TextView)findViewById(R.id.tipoEvento);

        Bundle bundle = getIntent().getExtras();
        int dia, mes, anio;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anio = bundle.getInt("anio");
        fecha.setText(dia + " - " + mes + " - " + anio);


        volverAgenda = (Button) findViewById(R.id.volverAgenda);
        crearEvento = (Button) findViewById(R.id.crearEvento);
        volverAgenda.setOnClickListener(this);
        crearEvento.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == crearEvento.getId()) {
            //guardar datos para crear evento
        } else {
            this.finish();
            return;
        }
    }
}