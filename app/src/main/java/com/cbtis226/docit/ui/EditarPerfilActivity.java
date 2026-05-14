package com.cbtis226.docit.ui;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Usuario;
import com.cbtis226.docit.net.ApiCliente;
import retrofit2.*;

public class EditarPerfilActivity extends AppCompatActivity {

    EditText eNom, eAp, eAm, eTel, eAlt, ePes;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_editar_perfil);

        eNom = findViewById(R.id.eNom);
        eAp = findViewById(R.id.eAp);
        eAm = findViewById(R.id.eAm);
        eTel = findViewById(R.id.eTel);
        eAlt = findViewById(R.id.eAlt);
        ePes = findViewById(R.id.ePes);

        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u != null){
            eNom.setText(u.nombre);
            eAp.setText(u.apellidoPaterno);
            eAm.setText(u.apellidoMaterno);
            eTel.setText(u.telefono);
            if(u.altura != null) eAlt.setText(String.valueOf(u.altura));
            if(u.peso != null) ePes.setText(String.valueOf(u.peso));
        }

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardar());
    }

    void guardar(){
        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u == null) return;
        u.nombre = eNom.getText().toString().trim();
        u.apellidoPaterno = eAp.getText().toString().trim();
        u.apellidoMaterno = eAm.getText().toString().trim();
        u.telefono = eTel.getText().toString().trim();
        try { u.altura = Double.parseDouble(eAlt.getText().toString()); } catch(Exception e){}
        try { u.peso = Double.parseDouble(ePes.getText().toString()); } catch(Exception e){}

        ApiCliente.api().actualizarUsuario(u.idUsuario, u).enqueue(new Callback<Usuario>() {
            @Override public void onResponse(Call<Usuario> c, Response<Usuario> r) {
                if(r.isSuccessful() && r.body() != null){
                    DoCitApp.x().guardarSesion(r.body());
                } else {
                    DoCitApp.x().guardarSesion(u);
                }
                Toast.makeText(EditarPerfilActivity.this, "Guardado", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override public void onFailure(Call<Usuario> c, Throwable t) {
                // guardamos local aunque el server no responda, ya luego sincroniza
                DoCitApp.x().guardarSesion(u);
                Toast.makeText(EditarPerfilActivity.this, "Guardado local (sin conexion)", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}