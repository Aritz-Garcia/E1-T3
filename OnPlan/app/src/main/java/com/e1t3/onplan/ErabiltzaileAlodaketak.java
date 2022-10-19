package com.e1t3.onplan;


import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ErabiltzaileAlodaketak  extends AppCompatActivity {

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dr = db.collection(Values.ERABILTZAILEAK).document();

    private ImageView mSetImage;
    private Button mOptionButton,mgorde;
    private String mPath;
    public TextView izena,abizena,dni,emaila,telefonoa;

    private Uri path;
    private StorageReference storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erabiltzaile_aldaketak);
        datuak();
        mSetImage = (ImageView) findViewById(R.id.limagen);
        mOptionButton = (Button) findViewById(R.id.belegir);
        storage = FirebaseStorage.getInstance().getReference();

        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });


        mgorde = (Button) findViewById(R.id.bgorde);
        mgorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.update(Values.ERABILTZAILEAK_EMAIL,emaila,Values.ERABILTZAILEAK_IZENA,izena,Values.ERABILTZAILEAK_NAN_IFZ,dni,Values.ERABILTZAILEAK_TELEFONOA,telefonoa)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("pan", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("pan", "Error updating document", e);
                            }
                        });
            }
        });

    }

    private void cargarimagen(Uri path){
        //StorageReference fotoRef = storage.child("FotosUsuario");
    }

    private void datuak(){
        izena = findViewById(R.id.textIzena);
        abizena = findViewById(R.id.textAbizena);
        dni = findViewById(R.id.textdni);
        emaila = findViewById(R.id.textEmaila);
        telefonoa= findViewById(R.id.textTelefonoa);
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

}
