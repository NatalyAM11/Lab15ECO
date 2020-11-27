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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    private EditText correoL, contraseñaL;
    private Button bLogin;
    private TextView noCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoL=findViewById(R.id.correoL);
        contraseñaL=findViewById(R.id.contraseñaL);
        bLogin=findViewById(R.id.bLogin);
        noCuenta=findViewById(R.id.noCuenta);


        auth=FirebaseAuth.getInstance();

        bLogin.setOnClickListener(this);
        noCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bLogin:

                        //No puede seguir hasta que llene todos los espacios
                        if(correoL.getText().toString().trim().isEmpty() || contraseñaL.getText().toString().isEmpty()){
                            runOnUiThread( ()-> Toast.makeText(this, " Debe llenar todos los campos", Toast.LENGTH_LONG).show());
                            return;
                        }

                        auth.signInWithEmailAndPassword(correoL.getText().toString(),contraseñaL.getText().toString())
                        .addOnCompleteListener(

                                task -> {
                                    if(task.isSuccessful()){
                                        Intent i= new Intent(this, MainActivity.class);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                        );
                break;

            case R.id.noCuenta:
                Intent i= new Intent(this, Signup.class);
                startActivity(i);
                break;
        }

    }
}