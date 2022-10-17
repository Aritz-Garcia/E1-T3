package com.e1t3.onplan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.e1t3.onplan.dao.DAOErabiltzaileak;
import com.e1t3.onplan.databinding.ActivityMainBinding;
import com.e1t3.onplan.model.Erabiltzailea;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.protobuf.DescriptorProtos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView tvNombreUsuario, tvEmailUsuario;
    private ImageView imagenUser;
    private Button btnCerrarSesion;
    private DAOErabiltzaileak daoErabiltzaileak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        List<Erabiltzailea> listE = daoErabiltzaileak.lortuErabiltzaileak();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_agenda, R.id.nav_salas, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        tvEmailUsuario = headerView.findViewById(R.id.tvUserEmail);
        tvNombreUsuario = headerView.findViewById(R.id.tvUserName);
        imagenUser = headerView.findViewById(R.id.ivUserImage);
        btnCerrarSesion = headerView.findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if (name == null || name.equals("")) {
            for(Erabiltzailea e : listE) {
                if (e.getEmail() == email) {
                    if (e.getEnpresaDa()) {
                        name = e.getIzena();
                        tvNombreUsuario.setText(name);
                    } else {
                        name = e.getIzena() + " " + e.getAbizena();
                        tvNombreUsuario.setText(name);
                    }
                }
            }

        } else {
            tvNombreUsuario.setText(name);
        }

        tvEmailUsuario.setText(email);
        if (photoUrl != null) {
            Picasso.get()
                    .load(photoUrl)
                    .into(imagenUser);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, com.e1t3.onplan.EzarpenakActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnCerrarSesion.getId()) {
            signOut();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}