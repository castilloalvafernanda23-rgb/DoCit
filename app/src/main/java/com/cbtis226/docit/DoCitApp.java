package com.cbtis226.docit;

import android.app.Application;
import android.content.SharedPreferences;
import com.cbtis226.docit.model.Usuario;
import com.google.gson.Gson;

public class DoCitApp extends Application {

    private static DoCitApp inst;
    public Usuario usuarioLogeado;

    @Override
    public void onCreate() {
        super.onCreate();
        inst = this;
        cargarSesion();
    }

    public static DoCitApp x(){ return inst; }

    public void guardarSesion(Usuario u){
        usuarioLogeado = u;
        SharedPreferences p = getSharedPreferences("docit", MODE_PRIVATE);
        p.edit().putString("user", new Gson().toJson(u)).apply();
    }

    private void cargarSesion(){
        SharedPreferences p = getSharedPreferences("docit", MODE_PRIVATE);
        String s = p.getString("user", null);
        if(s != null){
            usuarioLogeado = new Gson().fromJson(s, Usuario.class);
        }
    }

    public void cerrarSesion(){
        usuarioLogeado = null;
        getSharedPreferences("docit", MODE_PRIVATE).edit().clear().apply();
    }
}