package com.cbtis226.docit.model;

public class Cita {
    public String idCita;
    public String fecha;
    public String horaInicio;
    public String horaFin;
    public String estado; // pendiente, confirmada, cancelada, completada
    public String motivoConsulta;
    public String notas;
    public String fechaCreacion;

    public Usuario usuario;
    public Doctor doctor;
}