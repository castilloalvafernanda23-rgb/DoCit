package com.cbtis226.docit.model;

public class Usuario {
    public String idUsuario;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String correo;
    public String telefono;
    public String curp;
    public String contrasena;
    public String fotoPerfil;
    public String fechaNacimiento;
    public Double altura;
    public Double peso;
    public String fechaRegistro;
    public String estadoCuenta;

    public Usuario(){}

    public String nombreCompleto(){
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}