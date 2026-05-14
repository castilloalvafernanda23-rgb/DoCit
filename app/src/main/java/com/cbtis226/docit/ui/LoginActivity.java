package com.cbtis226.docit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Usuario;
import com.cbtis226.docit.net.ApiCliente;
import com.cbtis226.docit.net.ApiDocit;
import retrofit2.*;

public class LoginActivity extends AppCompatActivity {

    EditText txtCorreo, txtContra;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);

        findViewById(R.id.btnEntrar).setOnClickListener(v -> meterse());
        findViewById(R.id.btnIrRegistro).setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
        findViewById(R.id.txtOlvideContra).setOnClickListener(v ->
                Toast.makeText(this, "Próximamente xd", Toast.LENGTH_SHORT).show());
    }

    void meterse(){
        String c = txtCorreo.getText().toString().trim();
        String p = txtContra.getText().toString();
        if(c.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Llena los campos plis", Toast.LENGTH_SHORT).show();
            return;
        }
        // por si la api todavia no esta lista, dejamos un bypass de prueba
        if(c.equals("test") && p.equals("test")){
            Usuario fake = new Usuario();
            fake.idUsuario = "uid_demo";
            fake.nombre = "Demo";
            fake.apellidoPaterno = "Usuario";
            fake.apellidoMaterno = "Prueba";
            fake.correo = c;
            DoCitApp.x().guardarSesion(fake);
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
            return;
        }

        ApiCliente.api().login(new ApiDocit.LoginReq(c, p))
                .enqueue(new Callback<Usuario>() {
                    @Override public void onResponse(Call<Usuario> call, Response<Usuario> r) {
                        if(r.isSuccessful() && r.body() != null){
                            DoCitApp.x().guardarSesion(r.body());
                            startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "No hay conexion con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}