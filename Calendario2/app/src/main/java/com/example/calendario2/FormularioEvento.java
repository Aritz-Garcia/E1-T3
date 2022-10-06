package com.example.calendario2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calendario2.ui.dialog.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormularioEvento extends AppCompatActivity implements View.OnClickListener {

    private EditText nombreEvento, fechaIn, horaIn, fechaFin, horaFin, aforo, presupuesto;
    private TextView textDiaYHoraIn, textDiaYHoraFin, textAforo, textPresupuesto, textDescripción;
    private Button btnvolverAgenda, btnSiguiente;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_evento);



        nombreEvento = findViewById(R.id.nombreEvento);
        textDiaYHoraIn = findViewById(R.id.textDiaHoraIni);
        fechaIn = findViewById(R.id.fechaInicio);
        horaIn = findViewById(R.id.horaInicio);
        textDiaYHoraFin = findViewById(R.id.textDiaHoraFin);
        fechaFin = findViewById(R.id.fechaFin);
        horaFin = findViewById(R.id.horaFin);
        textAforo = findViewById(R.id.textAforo);
        aforo = findViewById(R.id.aforo);
        textPresupuesto = findViewById(R.id.textPresupuesto);
        presupuesto = findViewById(R.id.presupuesto);

        Bundle bundle = getIntent().getExtras();
        int dia, mes, anio;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anio = bundle.getInt("anio");

        String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
        fechaIn.setText(selectedDate);
        fechaIn.setOnClickListener(this);

        fechaFin.setOnClickListener(this);


        btnvolverAgenda = findViewById(R.id.volverAgenda);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnvolverAgenda.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSiguiente.getId()) {
            //guardar datos para crear evento
            this.finish();
            //return;
        } else if (view.getId() == btnvolverAgenda.getId()) {
            this.finish();
            //return;
        } else if (view.getId() == R.id.fechaInicio) {
            showDatePickerDialogInicio();
        } else if (view.getId() == R.id.fechaFin) {
            showDatePickerDialogFin();
            if (comprovacionTiempo1().after(comprovacionTiempo2())) {

            }
        }
    }

    private void showDatePickerDialogInicio() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
                fechaIn.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void showDatePickerDialogFin() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
                fechaFin.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");


    }

    private String dosDigitos(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private Date comprovacionTiempo1() {
        Date inicio = null;
        try {
            inicio = formato.parse(fechaIn.getText().toString());
            return inicio;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inicio;
    }

    private Date comprovacionTiempo2() {
        Date fin = null;
        try {
            fin = formato.parse(fechaFin.getText().toString());
            return fin;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fin;
    }
}