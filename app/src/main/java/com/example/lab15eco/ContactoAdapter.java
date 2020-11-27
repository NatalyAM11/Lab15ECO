package com.example.lab15eco;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {

    private ArrayList<Contacto> contactos;
    private FirebaseAuth auth;
    private String idU;

    public ContactoAdapter() {
        contactos = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
    }

    public void addContacto(Contacto contacto) {
        contactos.add(contacto);
        notifyDataSetChanged();
    }

    public void clear() {
        contactos.clear();
        notifyDataSetChanged();
    }


    public int getCount() {
        return contactos.size();
    }


    public Object getItem(int position) {
        return contactos.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int pos, View renglon, ViewGroup lista) {

        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View renglonView = inflater.inflate(R.layout.row, null);

        Contacto contacto = contactos.get(pos);

        TextView nombreCR = renglonView.findViewById(R.id.nombreCR);
        TextView telefonoCR = renglonView.findViewById(R.id.telefonoCR);
        Button bLlamar = renglonView.findViewById(R.id.bLlamar);
        Button bBorrar = renglonView.findViewById(R.id.bBorrar);

        nombreCR.setText(contacto.getNombre());
        telefonoCR.setText(contacto.getTelefono());

        //Busco el usuario que esta logueado para tener su id y poder borrar sus contactos
        if (auth.getCurrentUser() != null) {
            idU = auth.getCurrentUser().getUid();
        }

            //eliminar
            bBorrar.setOnClickListener(
                    (v) -> {
                        String idC = contacto.getId();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("contactos/" + idU).child(idC);
                        ref.setValue(null);

                    }
            );

        //llamada
        bLlamar.setOnClickListener(

                (v)->{
                    String tel="tel:"+contacto.getTelefono();
                    Intent i= new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(tel));
                    lista.getContext().startActivity(i);

                }
        );

            return renglonView;


        }
    }
