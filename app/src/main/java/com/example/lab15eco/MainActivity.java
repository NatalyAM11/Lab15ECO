package com.example.lab15eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase db;
    private FirebaseAuth auth;

    private EditText nombreC, telefonoC;
    private Button bAgregar, bCerrar;

    private ContactoAdapter adapter;

    private ListView listaContactos;

    String idU;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            goToLogin();

        }else{

            //Permiso de llamada
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CALL_PHONE
            }, 1 );


            nombreC= findViewById(R.id.nombreC);
            telefonoC=findViewById(R.id.telefonoC);
            bAgregar=findViewById(R.id.bAgregar);
            bCerrar=findViewById(R.id.bCerrar);
            listaContactos=findViewById(R.id.listaContactos);



            //Adapter
            adapter= new ContactoAdapter();
            listaContactos.setAdapter(adapter);


            recoverUser();

            Log.e(">>", idU);
            bAgregar.setOnClickListener(this);
            bCerrar.setOnClickListener(this);

        }

    }

    private void goToLogin(){
        Intent i= new Intent(this,Login.class);
        startActivity(i);
        finish();
    }


    //obtengo los datos del usuario logueado
    private void recoverUser(){
            if(auth.getCurrentUser()!=null){
                String id= auth.getCurrentUser().getUid();

                //Solo la igualo para poder sacarla del metodo
                idU=id;

                //Buscamos el id del usuario logueado en la rama contactos
                db.getReference().child("contactos/" + id).addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {

                                adapter.clear();

                                for(DataSnapshot child: data.getChildren()){
                                   Contacto contacto=child.getValue(Contacto.class);
                                    adapter.addContacto(contacto);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        }
                );
            }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAgregar:

                //valido que no pueda añadir contactos si no lleno los espacios
                if(nombreC.getText().toString().trim().isEmpty() || telefonoC.getText().toString().trim().isEmpty()){
                    runOnUiThread( ()-> Toast.makeText(this, " Debe llenar todos los campos", Toast.LENGTH_LONG).show());
                    return;
                }

                //Agregamos contactos

                String idC= db.getReference().child("contactos").push().getKey();
                DatabaseReference reference=db.getReference().child("contactos/" + idU).child(idC);

                Contacto contacto= new Contacto(
                        idC,
                        nombreC.getText().toString(),
                        telefonoC.getText().toString()
                );

                reference.setValue(contacto);

                break;

            case R.id.bCerrar:

                AlertDialog.Builder builder= new AlertDialog.Builder(this)
                        .setTitle("Cerrar Sesión")
                        .setMessage("¿Esta seguro que desea cerrar sesión?")
                        .setNegativeButton("NO", (dialog,id) ->{
                            dialog.dismiss();
                        })
                        .setPositiveButton("SI", (dialog,id)->{
                            auth.signOut();
                            goToLogin();
                        });

                builder.show();
                break;
        }
    }
}