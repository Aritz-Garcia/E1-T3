package com.example.calendario2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewEventos extends AppCompatActivity implements View.OnClickListener {

    private Button btnLineaTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eventos);
        btnLineaTiempo = findViewById(R.id.btnLineaTimepo);
        btnLineaTiempo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnLineaTiempo.getId()) {
            Intent i = new Intent(this, LineaDeTiempoEvento.class);
            startActivity(i);
        }
    }
}