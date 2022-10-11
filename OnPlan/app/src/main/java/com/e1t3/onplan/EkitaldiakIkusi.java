package com.e1t3.onplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EkitaldiakIkusi extends AppCompatActivity implements View.OnClickListener {

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
            Intent i = new Intent(this, com.e1t3.onplan.EkitaldiActivity.class);
            startActivity(i);
        }
    }
}