package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignIn";
    public TextView izena,abizena,dni,telefonoa,emaila,pasahitza1,pasahitza2, radioerror;
    public  RadioButton aukeratuta, aukeratuta2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public boolean[] egokia = new boolean[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aukeratuta = (RadioButton) findViewById(R.id.RBPersona);
        aukeratuta2= (RadioButton) findViewById(R.id.RBEmpresa);
        radioerror = (TextView) findViewById(R.id.radioerror);

        Button button = (Button) findViewById(R.id.GordeBotoia);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                if(aukeratuta.isChecked() ||  aukeratuta2.isChecked()) {
                    radioerror.setVisibility(View.INVISIBLE);
                    datuakhartu();
                    egokia[0] = stringIrakurri(izena.getText().toString(), findViewById(R.id.izenaTextua));
                    if (aukeratuta.isChecked()) {
                        abizena = (TextView) findViewById(R.id.AbizenaTextua);
                        egokia[1] = stringIrakurri(abizena.getText().toString(), findViewById(R.id.AbizenaTextua));
                    } else {
                        egokia[1] = true;
                    }
                    egokia[2] = dnikonprobatu(dni.getText().toString(), findViewById(R.id.DNITextua));
                    egokia[3] = zenbakiaIrakurri(telefonoa.getText().toString(), findViewById(R.id.TelefonoTextua));
                    egokia[4] = emailkonprobatu(emaila.getText().toString(), findViewById(R.id.EmailTextua));
                    egokia[5] = pasahitzaIrakurri(pasahitza1.getText().toString(), findViewById(R.id.Pasahitza1Textua));
                    egokia[6] = pasahitzaKonfirmatu(pasahitza1.getText().toString(), pasahitza2.getText().toString(), findViewById(R.id.Pasahitza2Textua));
                    if (egokia[0] && egokia[1] && egokia[2] && egokia[3] && egokia[4] && egokia[5]) {
                       datuakbidali();
                    }
                }else{
                    radioerror.setVisibility(View.VISIBLE);
                    }
            }
        });


    }


    //Aukeratzen badu Pertsona radiobutton Abizena jartzeko laukia agertuko da
    public void radiobuttonkonporbatu(View v){
        if(aukeratuta.isChecked()){
            findViewById(R.id.AbizenaTextua).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.AbizenaTextua).setVisibility(View.INVISIBLE);
        }
    }

    //datuak hartu egiten ditugu activity_mainetik geroago konprobatzeko bere egitura
    public void datuakhartu(){
        izena = (TextView) findViewById(R.id.izenaTextua);
        dni = (TextView) findViewById(R.id.DNITextua);
        telefonoa = (TextView)  findViewById(R.id.TelefonoTextua);
        emaila = (TextView) findViewById(R.id.EmailTextua);
        pasahitza1 = (TextView) findViewById(R.id.Pasahitza1Textua);
        pasahitza2 = (TextView) findViewById(R.id.Pasahitza2Textua);
    }

    public boolean konprobatuErabiltzailea( ){

        db.collection("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return true;

    }

    public void datuakbidali(){
        HashMap<String, String> usuario = new HashMap();
        usuario.put("nombre",izena.toString());
        if (aukeratuta.isChecked()) {
            usuario.put("apellido", abizena.toString());
        }
        usuario.put("telefono",telefonoa.toString());
        usuario.put("correo",emaila.toString());
        usuario.put("dni_nif",dni.toString());

        db.collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
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


    public boolean dnikonprobatu(String textua, EditText text){
            if( textua.length()==0 )  {
                text.setError("Beharrezko kanpua");
                return false;
            }else if((!textua.matches("[0-9]{7,8}[A-Z a-z]"))){
                text.setError("00000000A formatua");
                return false;
            }else{
                return true;
            }
    }


    public boolean emailkonprobatu(String textua, EditText text){
            if( textua.length()==0 )  {
                text.setError("Beharrezko kanpua");
                return false;
            }else if((!textua.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))){
                text.setError("aaaaa@aaaa.aaa formatua");
                return false;
            }else if(!konprobatuErabiltzailea()){
                text.setError("Ezin da errepikatu emaila");
                    return false;
            }else{
                return true;
            }
    }


    public boolean pasahitzaIrakurri(String cadena, EditText text){
        if(cadena.length()==0){
            text.setError("Beharrezko kanpua");
            return false;
        }else {
            return true;
        }
    }

    //devuelve un true o false si la primera constrase�a y la segunda contrase�a son iguales
    public boolean pasahitzaKonfirmatu(String pasahitza1, String pasahitza2, EditText text) {
        if(pasahitza2.length()==0) {
            text.setError("Beharrezko kanpua");
            return false;
        }else if(!pasahitza1.equals(pasahitza2)){
            text.setError("Bi pasahitzak berdinak izan behar dira");
            return false;
        }else {
            return true;
        }
    }


}