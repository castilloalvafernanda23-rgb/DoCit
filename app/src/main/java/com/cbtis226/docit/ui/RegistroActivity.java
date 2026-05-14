package com.cbtis226.docit.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Usuario;
import com.cbtis226.docit.net.ApiCliente;
import java.util.Calendar;
import retrofit2.*;

public class RegistroActivity extends AppCompatActivity {

    EditText eNombre, ePaterno, eMaterno, eCorreo, eTel, eCurp, eFechaNac, eAltura, ePeso, eContra, eContra2;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_registro);

        eNombre = findViewById(R.id.eNombre);
        ePaterno = findViewById(R.id.ePaterno);
        eMaterno = findViewById(R.id.eMaterno);
        eCorreo = findViewById(R.id.eCorreo);
        eTel = findViewById(R.id.eTel);
        eCurp = findViewById(R.id.eCurp);
        eFechaNac = findViewById(R.id.eFechaNac);
        eAltura = findViewById(R.id.eAltura);
        ePeso = findViewById(R.id.ePeso);
        eContra = findViewById(R.id.eContra);
        eContra2 = findViewById(R.id.eContra2);

        eFechaNac.setOnClickListener(v -> pickerFecha());
        findViewById(R.id.btnRegistrar).setOnClickListener(v -> mandarRegistro());
    }

    void pickerFecha(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -20);
        new DatePickerDialog(this, (view, y, m, d) -> {
            String s = String.format("%02d/%02d/%04d", d, m+1, y);
            eFechaNac.setText(s);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    void mandarRegistro(){
        // checadas basicas
        if(eNombre.getText().toString().isEmpty() || eCorreo.getText().toString().isEmpty()
                || eContra.getText().toString().isEmpty()){
            Toast.makeText(this, "Llena lo importante porfa", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!eContra.getText().toString().equals(eContra2.getText().toString())){
            Toast.makeText(this, "Las contras no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario u = new Usuario();
        u.nombre = eNombre.getText().toString().trim();
        u.apellidoPaterno = ePaterno.getText().toString().trim();
        u.apellidoMaterno = eMaterno.getText().toString().trim();
        u.correo = eCorreo.getText().toString().trim();
        u.telefono = eTel.getText().toString().trim();
        u.curp = eCurp.getText().toString().trim();
        u.fechaNacimiento = eFechaNac.getText().toString().trim();
        u.contrasena = eContra.getText().toString();
        try { u.altura = Double.parseDouble(eAltura.getText().toString()); } catch(Exception e){}
        try { u.peso = Double.parseDouble(ePeso.getText().toString()); } catch(Exception e){}

        ApiCliente.api().registrar(u).enqueue(new Callback<Usuario>() {
            @Override public void onResponse(Call<Usuario> call, Response<Usuario> r) {
                if(r.isSuccessful() && r.body() != null){
                    DoCitApp.x().guardarSesion(r.body());
                    Toast.makeText(RegistroActivity.this, "Listo, bienvenido!", Toast.LENGTH_SHORT).show();
                    startActivity(new android.content.Intent(RegistroActivity.this, PrincipalActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(RegistroActivity.this, "No se pudo, intenta de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Sin conexion al server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}