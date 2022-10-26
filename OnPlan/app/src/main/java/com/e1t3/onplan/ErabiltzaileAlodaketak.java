package com.e1t3.onplan;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.e1t3.onplan.dao.DAOEkitaldiak;
import com.e1t3.onplan.dao.DAOErabiltzaileak;
import com.e1t3.onplan.dao.DAOGertaerak;
import com.e1t3.onplan.model.Ekitaldia;
import com.e1t3.onplan.model.Erabiltzailea;
import com.e1t3.onplan.model.Gertaera;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ErabiltzaileAlodaketak  extends AppCompatActivity {

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dr = db.collection(Values.ERABILTZAILEAK).document();
    private DocumentReference id;

    private ImageView mSetImage;
    private Button mOptionButton,mgorde,mborrar;
    private String mPath,email;
    public TextView izena,abizena,dni,emaila,telefonoa;
    private boolean empresa_da;
    private Uri path;
    private FirebaseUser user;
    private StorageReference storage;
    private SharedPreferences erabiltzaileDatuak;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erabiltzaile_aldaketak);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        setDayNight();

         user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
        datuak();
        mSetImage = (ImageView) findViewById(R.id.limagen);
        mOptionButton = (Button) findViewById(R.id.belegir);
        storage = FirebaseStorage.getInstance().getReference();

        erabiltzaileDatuak = getSharedPreferences(Values.ERABILTZAILEAK, Context.MODE_PRIVATE);
        editor = erabiltzaileDatuak.edit();
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });


        mborrar = (Button) findViewById(R.id.beleminar);
        mborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEkitaldiakId();
                DAOErabiltzaileak daoErabiltzaileak = new DAOErabiltzaileak();
                daoErabiltzaileak.ezabatuErabiltzailea(erabiltzaileDatuak.getString("id", ""));
                borrarUsuario();
            }
        });

        mgorde = (Button) findViewById(R.id.bgorde);
        mgorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean egokia[] = new boolean[3];
                egokia[0] = stringIrakurri(izena.getText().toString(),findViewById(R.id.textIzena));
                egokia[1] = zenbakiaIrakurri(telefonoa.getText().toString(),findViewById(R.id.textTelefonoa));
                if(empresa_da){
                   egokia[2]=true;
                }else{
                    egokia[2] = stringIrakurri(abizena.getText().toString(),findViewById(R.id.textIzena));
                }
                if (egokia[0] && egokia[1] && egokia[2]) {
                    datuakaldatu();
                }
            }

        });



    }

    private void datuakaldatu(){
        db.collection(Values.ERABILTZAILEAK)
                .whereEqualTo(Values.ERABILTZAILEAK_EMAIL, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Erabiltzailea erabiltzailea = new Erabiltzailea(document);

                                DocumentReference actualizar = db.collection(Values.ERABILTZAILEAK).document(erabiltzailea.getId());
                               id = db.collection(Values.ERABILTZAILEAK).document(erabiltzailea.getId());

                                if(empresa_da){
                                    actualizar.update(Values.ERABILTZAILEAK_IZENA,izena.getText().toString(),Values.ERABILTZAILEAK_TELEFONOA,telefonoa.getText().toString());
                                }else{
                                    actualizar.update(Values.ERABILTZAILEAK_IZENA,izena.getText().toString(),Values.ERABILTZAILEAK_TELEFONOA,telefonoa.getText().toString(),Values.ERABILTZAILEAK_ABIZENA,abizena.getText().toString());
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void cargarimagen(Uri path){
        //StorageReference fotoRef = storage.child("FotosUsuario");
    }

    private void datuak(){
        izena = findViewById(R.id.textIzena);
        telefonoa =  findViewById(R.id.textTelefonoa);
        dni =  findViewById(R.id.textdni);
        dni.setEnabled(false);
        emaila =   findViewById(R.id.textEmaila);
        emaila.setEnabled(false);
        abizena =  findViewById(R.id.textAbizena2);
        db.collection(Values.ERABILTZAILEAK)
                .whereEqualTo(Values.ERABILTZAILEAK_EMAIL, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Erabiltzailea erabiltzailea = new Erabiltzailea(document);
                                izena.setText(erabiltzailea.getIzena());
                                emaila.setText(erabiltzailea.getEmail());
                                dni.setText(erabiltzailea.getNanIfz());
                                telefonoa.setText(erabiltzailea.getTelefonoa());
                                if (erabiltzailea.getEnpresaDa()) {
                                    abizena.setVisibility(View.INVISIBLE);
                                } else {
                                    abizena.setVisibility(View.VISIBLE);
                                    abizena.setText(erabiltzailea.getAbizena());
                                }
                                empresa_da = erabiltzailea.getEnpresaDa();

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public boolean stringIrakurri(String textua, EditText text){
        if( textua.length()==0 )  {
            text.setError("Beharrezko kanpua");
            return false;
        }else if((!textua.matches("[a-zA-Z ]+\\.?"))){
            text.setError("Bakarrik letrak");
            return false;
        }else{
            return true;
        }
    }

    public boolean zenbakiaIrakurri(String textua, EditText text){
        if( textua.length()==0 ) {
            text.setError("Beharrezko kanpua");
            return false;
        }else if(textua.length()!=9) {
            text.setError("Bederatziko luzeera");
            return false;
        }else if((!textua.matches("[0-9]+\\.?")) ){
            text.setError("Bakarrik zenbakiak");
            return false;
        }else{
            return true;
        }
    }

    private void showOptions() {
        final CharSequence[] option = { "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ErabiltzaileAlodaketak.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    path = data.getData();
                    mSetImage.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ErabiltzaileAlodaketak.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ErabiltzaileAlodaketak.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    private void getEkitaldiakId() {
        db.collection(Values.EKITALDIAK)
                .whereEqualTo(Values.EKITALDIAK_ERABILTZAILEA, erabiltzaileDatuak.getString("id", ""))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ekitaldia ekitaldia = new Ekitaldia(document);

                                for (int i = 0; i<ekitaldia.getGertaerak().size(); i++) {
                                    DAOGertaerak daoGertaerak = new DAOGertaerak();
                                    daoGertaerak.ezabatuGertaeraIdz(ekitaldia.getGertaerak().get(i));
                                }
                                DAOEkitaldiak daoEkitaldiak = new DAOEkitaldiak();
                                daoEkitaldiak.ezabatuEkitaldiaId(ekitaldia.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void borrarUsuario() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG,"OK! Works fine!");
                    startActivity(new Intent(ErabiltzaileAlodaketak.this, Login.class));
                    finish();
                } else {
                    Log.w(TAG,"Something is wrong!");
                }
            }
        });
    }

    public void setDayNight() {
        boolean oscuro = sharedPreferences.getBoolean("oscuro", false);
        if (oscuro) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
