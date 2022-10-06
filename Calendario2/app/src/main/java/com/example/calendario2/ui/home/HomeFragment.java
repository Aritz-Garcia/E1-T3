package com.example.calendario2.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calendario2.FormularioEvento;
import com.example.calendario2.R;
import com.example.calendario2.ViewEventos;
import com.example.calendario2.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements CalendarView.OnDateChangeListener{

    private FragmentHomeBinding binding;
    private CalendarView calendarview;
    private Button btnLineaTimpo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calendarview = root.findViewById(R.id.calendario);
        calendarview.setOnDateChangeListener(this);

        btnLineaTimpo = root.findViewById(R.id.sumarEventoBot);
        btnLineaTimpo.setOnClickListener((View.OnClickListener) this);

        return root;
    }

    public void onSelectedDayChange (CalendarView calendarview, int i, int i1, int i2) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        CharSequence []items = new CharSequence[3];
        items[0] = "Agregar evento";
        items[1] = "Ver eventos";
        items[2] = "Cancelar";

        final int dia, mes, anio;
        anio = i;
        mes = i1 + 1;
        dia = i2;

        builder.setTitle("Seleccione una tarea")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //agregar evento
                            Intent intent = new Intent(getActivity(), FormularioEvento.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (i == 1) {
                            //ver evento
                            Intent intent = new Intent(getActivity(), ViewEventos.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnLineaTimpo.getId()) {
            Intent i = new Intent(this, LineaDeTiempoEvento.class);
            startActivity(i);
        }
    }
}