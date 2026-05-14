package com.cbtis226.docit.net;

import com.cbtis226.docit.model.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiDocit {

    // sesion
    @POST("login")
    Call<Usuario> login(@Body LoginReq req);

    @POST("usuarios")
    Call<Usuario> registrar(@Body Usuario u);

    @PUT("usuarios/{id}")
    Call<Usuario> actualizarUsuario(@Path("id") String id, @Body Usuario u);

    // doctores y especialidades
    @GET("especialidades")
    Call<List<Especialidad>> especialidades();

    @GET("doctores")
    Call<List<Doctor>> doctores(@Query("especialidad") String esp,
                                @Query("nombre") String nombre,
                                @Query("consultorio") String consul);

    @GET("doctores/{id}")
    Call<Doctor> doctor(@Path("id") String id);

    // citas
    @GET("citas/usuario/{id}")
    Call<List<Cita>> misCitas(@Path("id") String idUsuario);

    @POST("citas")
    Call<Cita> crearCita(@Body Cita c);

    @PUT("citas/{id}")
    Call<Cita> updateCita(@Path("id") String id, @Body Cita c);

    @GET("doctores/{id}/horarios")
    Call<List<HorarioDisponible>> horariosDoctor(@Path("id") String idDoc,
                                                 @Query("fecha") String fecha);

    @GET("doctores/{id}/resenas")
    Call<List<Resena>> resenasDoctor(@Path("id") String idDoc);

    @POST("resenas")
    Call<Resena> crearResena(@Body Resena r);

    class LoginReq {
        public String correo;
        public String contrasena;
        public LoginReq(String c, String p){ correo=c; contrasena=p; }
    }
}