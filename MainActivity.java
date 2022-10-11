package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignIn";
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSingInClient;
    private FirebaseAuth mAuth;

    public String izena2,abizena2,dni2,telefonoa2,emaila2,pasahitza12;
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
        Object signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();



        //Configuar Google Sing In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        //Crear un GoogleSingInClient con las opciones especificadas por gso.
         mGoogleSingInClient = GoogleSignIn.getClient(this, gso);

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(view -> signIn());

         mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                try{
                    GoogleSignInAccount account= task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle"+account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                }catch(ApiException e){
                    Log.w(TAG, "Google sign in faied",e);
                }
            }else{
                Log.d(TAG,"Error, login no exitos:" +task.getException().toString());
                Toast.makeText(this,"Ocurrio un error,"+task.getException().toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG,"signInWithCredential:succesc");
                    }else{
                        Log.w(TAG,"signInWithCredential:failure", task.getException());
                    }
                });
    }

    private void signIn(){
        Intent singInIntent= mGoogleSingInClient.getSignInIntent();
        startActivityForResult(singInIntent,RC_SIGN_IN);
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
        izena = (EditText) findViewById(R.id.izenaTextua);
        dni = (TextView) findViewById(R.id.DNITextua);
        telefonoa = (TextView)  findViewById(R.id.TelefonoTextua);
        emaila = (TextView) findViewById(R.id.EmailTextua);
        pasahitza1 = (TextView) findViewById(R.id.Pasahitza1Textua);
        pasahitza2 = (TextView) findViewById(R.id.Pasahitza2Textua);
    }

    public void stringlortu(){
        izena2 = izena.toString();
        abizena2 = abizena.toString();
        dni2 = dni.toString();
        telefonoa2 = telefonoa.toString();
        emaila2 = emaila.toString();
        pasahitza12 = pasahitza1.toString();
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
        stringlortu();
        CollectionReference usuarios = db.collection("usuarios");
        Map<String, Object> usuario = new HashMap();
        usuario.put("nombre",izena2);
        if (aukeratuta.isChecked()) {
            usuario.put("apellido", abizena2);
        }
        usuario.put("telefono",telefonoa2);
        usuario.put("correo",emaila2);
        usuario.put("dni_nif",dni2);


        usuarios.document().set(usuario);
    }

        /*btn.setOnClickListener(new View.onClickListener(){
            @Override
            public void Onclick(View view){
                db.collection("usuarios").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void OnComplete(@NonNull Task<QuerySnapshot> task){
                        if(task.isSuccessFull()){
                            for(QueryDocumentSnapshot document : task getResult()){
                                Log.d(Tag, document.getId());
                            }
                        }
                    }
                });
            }
        });*/

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

    /*
    meter datos en la base de datos firebase
     */
}