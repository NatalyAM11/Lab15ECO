package com.example.lab15eco;

public class Contacto {

    private String id,nombre,telefono;

    public Contacto(String id, String nombre, String telefono){
        this.id=id;
        this.nombre=nombre;
        this.telefono=telefono;

    }

    public Contacto(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
