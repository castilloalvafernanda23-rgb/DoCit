package com.cbtis226.docit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Usuario;
import com.squareup.picasso.Picasso;

public class PerfilUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_perfil_usuario);

        Usuario u = DoCitApp.x().usuarioLogeado;
        TextView lblN = findViewById(R.id.lblNombre);
        TextView lblC = findViewById(R.id.lblCorreo);
        ImageView img = findViewById(R.id.imgPerfil);

        if(u != null){
            lblN.setText(u.nombreCompleto());
            lblC.setText(u.correo);
            if(u.fotoPerfil != null && !u.fotoPerfil.isEmpty()){
                Picasso.get().load(u.fotoPerfil).placeholder(R.drawable.ic_persona).into(img);
            }
        }

        View rInfo = findViewById(R.id.rowInfo);
        View rCitas = findViewById(R.id.rowCitas);
        View rEdit = findViewById(R.id.rowEditar);
        View rOut = findViewById(R.id.rowCerrar);

        ((TextView)rInfo.findViewById(R.id.txtRow)).setText("Información personal");
        ((TextView)rCitas.findViewById(R.id.txtRow)).setText("Historial de citas");
        ((TextView)rEdit.findViewById(R.id.txtRow)).setText("Editar perfil");
        ((TextView)rOut.findViewById(R.id.txtRow)).setText("Cerrar sesión");

        ((ImageView)rInfo.findViewById(R.id.icRow)).setImageResource(R.drawable.ic_persona);
        ((ImageView)rCitas.findViewById(R.id.icRow)).setImageResource(R.drawable.ic_historial);
        ((ImageView)rEdit.findViewById(R.id.icRow)).setImageResource(R.drawable.ic_editar);
        ((ImageView)rOut.findViewById(R.id.icRow)).setImageResource(R.drawable.ic_salir);

        rInfo.setOnClickListener(v -> mostrarInfo());
        rCitas.setOnClickListener(v -> startActivity(new Intent(this, HistorialActivity.class)));
        rEdit.setOnClickListener(v -> startActivity(new Intent(this, EditarPerfilActivity.class)));
        rOut.setOnClickListener(v -> cerrarSesion());
    }

    void mostrarInfo(){
        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u == null) return;
        String txt = "Nombre: " + u.nombreCompleto() +
                "\nCorreo: " + u.correo +
                "\nTel: " + (u.telefono == null ? "-" : u.telefono) +
                "\nCURP: " + (u.curp == null ? "-" : u.curp) +
                "\nNacimiento: " + (u.fechaNacimiento == null ? "-" : u.fechaNacimiento) +
                "\nAltura: " + (u.altura == null ? "-" : u.altura + " m") +
                "\nPeso: " + (u.peso == null ? "-" : u.peso + " kg");
        new AlertDialog.Builder(this).setTitle("Mi información").setMessage(txt)
                .setPositiveButton("Ok", null).show();
    }

    void cerrarSesion(){
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("Seguro que quieres salir?")
                .setPositiveButton("Sí", (d,w) -> {
                    DoCitApp.x().cerrarSesion();
                    Intent i = new Intent(this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override protected void onResume() {
        super.onResume();
        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u != null){
            ((TextView)findViewById(R.id.lblNombre)).setText(u.nombreCompleto());
        }
    }
}