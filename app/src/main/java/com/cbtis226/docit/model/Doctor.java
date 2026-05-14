package com.cbtis226.docit.model;

import java.util.List;

public class Doctor {
    public String idDoctor;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String correo;
    public String telefono;
    public String cedulaProfesional;
    public String fotoPerfil;
    public String descripcion;
    public String whatsapp;
    public Double promedioCalificacion;
    public String estadoCuenta;
    public String fechaRegistro;

    public Especialidad especialidad;
    public Consultorio consultorio;
    public List<Servicio> servicios;
    public List<HorarioDisponible> horarios;
    public List<Imagen> imagenes;
    public List<Resena> resenas;

    public String nombreDr(){
        return "Dr. " + nombre + " " + apellidoPaterno;
    }
}