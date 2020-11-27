package com.example.lab15eco;

public class Usuario {

    private String id, nombre,correo,contraseña, telefono;

    public Usuario (String id, String nombre, String correo, String contraseña, String telefono){
        this.id=id;
        this.nombre=nombre;
        this.correo=correo;
        this.contraseña=contraseña;
        this.telefono=telefono;
    }

    public Usuario (){

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
