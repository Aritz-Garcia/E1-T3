package com.e1t3.onplan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.ui.dialog.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EkitaldiInprimakia extends AppCompatActivity implements View.OnClickListener {

    private EditText etNombreEvento, etFechaIn, etHoraIn, etFechaFin, etHoraFin, etAforo, etPresupuesto, etDescripcion;
    private TextView tvDiaHoraIn, tvDiaHoraFin, tvAforo, tvPresupuesto, tvDescripcion;
    private Button btnSiguiente, btnVolverAgenda;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_evento);

        etNombreEvento = findViewById(R.id.etNombreEvento);
        tvDiaHoraIn = findViewById(R.id.tvDiaHoraIni);
        etFechaIn = findViewById(R.id.etFechaInicio);
        etHoraIn = findViewById(R.id.etHoraInicio);
        tvDiaHoraFin = findViewById(R.id.tvDiaHoraFin);
        etFechaFin = findViewById(R.id.etFechaFin);
        etHoraFin = findViewById(R.id.etHoraFin);
        tvAforo = findViewById(R.id.tvAforo);
        etAforo = findViewById(R.id.etAforo);
        tvPresupuesto = findViewById(R.id.tvPresupuesto);
        etPresupuesto = findViewById(R.id.etPresupuesto);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        etDescripcion = findViewById(R.id.etDescripcion);

        Bundle bundle = getIntent().getExtras();
        int dia, mes, anio;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anio = bundle.getInt("anio");

        String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes) + "/" + anio;
        etFechaIn.setText(selectedDate);
        etFechaIn.setOnClickListener(this);
        etFechaFin.setOnClickListener(this);

        String ordua = "00:00";
        etHoraIn.setText(ordua);
        etHoraIn.setOnClickListener(this);
        etHoraFin.setText(ordua);
        etHoraFin.setOnClickListener(this);

        btnVolverAgenda = findViewById(R.id.btnVolverAgenda);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnVolverAgenda.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSiguiente.getId()) {
            //guardar datos para crear evento
            Date comprovacion = comprovacion();
            if (comprovacionTiempo2().equals(comprovacion)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No hay datos metidos en la fecha de fin. Vuelve a intentarlo")
                        .setTitle("Error")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                etFechaFin.setText("");
                                String ordua = "00:00";
                                etHoraFin.setText(ordua);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else if (comprovacionTiempo1().after(comprovacionTiempo2()) || comprovacionTiempo1().equals(comprovacionTiempo2())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("La fecha de fin es anterior o igual a la fecha de inicio. Vuelve a intentarlo")
                        .setTitle("Error")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                etFechaFin.setText("");
                                String ordua = "00:00";
                                etHoraFin.setText(ordua);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Intent i = new Intent(this, com.e1t3.onplan.EkitaldiInprimakiaGelak.class);
                startActivity(i);
            }
        } else if (view.getId() == btnVolverAgenda.getId()) {
            this.finish();
        } else if (view.getId() == etFechaIn.getId()) {
            showDatePickerDialogInicio();
        } else if (view.getId() == etFechaFin.getId()) {
            showDatePickerDialogFin();
        } else if (view.getId() == etHoraIn.getId()) {
            showTimePickerDialog(etHoraIn);
        } else if (view.getId() == etHoraFin.getId()) {
            showTimePickerDialog(etHoraFin);
        }
    }

    private void showDatePickerDialogInicio() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
                etFechaIn.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void showDatePickerDialogFin() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                final String selectedDate = dosDigitos(dia) + "/" + dosDigitos(mes+1) + "/" + anio;
                etFechaFin.setText(selectedDate);
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
            inicio = formato.parse(etFechaIn.getText().toString() + " " + etHoraIn.getText().toString());
            return inicio;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inicio;
    }

    private Date comprovacionTiempo2() {
        Date fin = null;
        try {
            fin = formato.parse(etFechaFin.getText().toString() + " " + etHoraFin.getText().toString());
            return fin;
        } catch (ParseException e) {
            if (fin == null) {
                try {
                    fin = formato.parse("01/01/1800 00:00");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return fin;
    }

    private Date comprovacion() {
        Date fin = null;
        try {
            fin = formato.parse("01/01/1800 00:00");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fin;
    }

    private void showTimePickerDialog(EditText editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String denbora = dosDigitos(hourOfDay) + ":" + dosDigitos(minutes);
                editText.setText(denbora);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }
}