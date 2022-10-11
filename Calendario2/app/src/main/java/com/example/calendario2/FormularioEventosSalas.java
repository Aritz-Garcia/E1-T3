package com.example.calendario2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormularioEventosSalas extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolverAtras, btnSiguienteSalas;
    private RecyclerView rvSalas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_eventos_salas);

        btnVolverAtras = findViewById(R.id.btnVolverAtras);
        btnSiguienteSalas = findViewById(R.id.btnSiguinteSalas);
        btnVolverAtras.setOnClickListener(this);
        btnSiguienteSalas.setOnClickListener(this);

    }

    public void onClick(View view){
        if (view.getId() == btnVolverAtras.getId()) {
            this.finish();
        } else if (view.getId() == btnSiguienteSalas.getId()) {
            //Seleccion tipo de evento
            Intent i = new Intent(this, FormularioTipoDeEvento.class);
            startActivity(i);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}