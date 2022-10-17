package com.e1t3.onplan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.databinding.ActivityEkitaldiBinding;
import com.e1t3.onplan.model.Ekitaldia;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

public class EkitaldiActivity extends AppCompatActivity {

    //Layout Android elementuak
    private ActivityEkitaldiBinding binding;
    private LinearLayout linearLayout = new LinearLayout(this);

    // Datubaserako objektuak
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Ekitaldia ekitaldia;
    private DAOEkitaldiak daoEkitaldiak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEkitaldiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Ekitaldia");
        getSupportActionBar().setSubtitle("sairam");

        this.daoEkitaldiak.lortuEkitaldiaIdz("1Dm6GUbJi5y6KEy6leyl");
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUp(){
        LinearLayout gertaeraLinear = new LinearLayout(this);
            TextView ordua = new TextView(this);
            LinearLayout botoiaLinear = new LinearLayout(this);
                Button checker = new Button(this);
            TextView izena = new TextView(this);
            TextView deskribapena = new TextView(this);
    }

}