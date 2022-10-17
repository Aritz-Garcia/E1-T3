package com.e1t3.onplan.ui.kontaktua;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e1t3.onplan.R;
import com.e1t3.onplan.databinding.FragmentHomeBinding;
import com.e1t3.onplan.databinding.FragmentKontaktuaBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class KontaktuaFragment extends Fragment {

    private FragmentKontaktuaBinding binding;
    private MapView mvKontaktua;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKontaktuaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mvKontaktua = root.findViewById(R.id.mvKontaktua);

        return root;
    }

    public void onMapReady (GoogleMap googleMap) {
        LatLng errekamari= new com.google.android.gms.maps.model.LatLng(43.27162, -2.94474);
        googleMap.addMarker(new MarkerOptions()
                .position(errekamari)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onStart() {
        super.onStart();
    }
}