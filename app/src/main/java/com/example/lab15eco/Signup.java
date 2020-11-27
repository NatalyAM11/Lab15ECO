package com.example.lab15eco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private EditText nombreS, correoS, contraseñaS, contraseña2S, telefonoS;
    private Button bRegistro;
    private TextView yaCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        nombreS= findViewById(R.id.nombreS);
        correoS=findViewById(R.id.correoS);
        contraseñaS=findViewById(R.id.contraseñaS);
        contraseña2S=findViewById(R.id.contraseña2S);
        telefonoS=findViewById(R.id.telefonoS);
        bRegistro=findViewById(R.id.bRegistro);
        yaCuenta=findViewById(R.id.yaCuenta);

        bRegistro.setOnClickListener(this);
        yaCuenta.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bRegistro:

                //valido que no pueda añadir contactos si no lleno los espacios
                if(nombreS.getText().toString().trim().isEmpty() || correoS.getText().toString().trim().isEmpty() ||
                    contraseñaS.getText().toString().trim().isEmpty() || contraseña2S.getText().toString().trim().isEmpty() || telefonoS.getText().toString().trim().isEmpty()){

                    runOnUiThread( ()-> Toast.makeText(this, " Debe llenar todos los campos", Toast.LENGTH_LONG).show());
                    return;
                }

                //Si las contraseñas no coinciden tampoco pasa
                /*if(contraseñaS.getText().toString()!= contraseña2S.getText().toString()){
                    runOnUiThread( ()-> Toast.makeText(this, " Las contraseñas no coinciden", Toast.LENGTH_LONG).show());
                    return;
                }*/


                    auth.createUserWithEmailAndPassword(correoS.getText().toString(),contraseñaS.getText().toString())
                    .addOnCompleteListener(

                            task->{
                                if(task.isSuccessful()){
                                    //Obtengo el id
                                    String id= auth.getCurrentUser().getUid();

                                    //Creo el usuario
                                    Usuario usuario= new Usuario(
                                        id,
                                        nombreS.getText().toString(),
                                        correoS.getText().toString(),
                                        contraseñaS.getText().toString(),
                                        telefonoS.getText().toString()

                                    );

                                   //Lo envio a Firebase
                                    db.getReference().child("usuarios").child(id).setValue(usuario)
                                        .addOnCompleteListener(
                                                taskDb->{
                                                    if(taskDb.isSuccessful()){
                                                        Intent i= new Intent(this, MainActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                        );

                                }else{
                                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                break;

            case R.id.yaCuenta:
                finish();
                break;
        }

    }
}