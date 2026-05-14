package com.cbtis226.docit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.adapters.EspecialidadAdapter;
import com.cbtis226.docit.adapters.CitaAdapter;
import com.cbtis226.docit.model.*;
import com.cbtis226.docit.net.ApiCliente;
import java.util.*;
import retrofit2.*;

public class PrincipalActivity extends AppCompatActivity {

    RecyclerView rvEsp, rvCitas;
    TextView lblNoCitas, lblNombre;
    List<Especialidad> listaEsp = new ArrayList<>();
    List<Cita> listaCitas = new ArrayList<>();
    EspecialidadAdapter adEsp;
    CitaAdapter adCita;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_principal);

        lblNombre = findViewById(R.id.lblNombre);
        rvEsp = findViewById(R.id.rvEspecialidades);
        rvCitas = findViewById(R.id.rvProximas);
        lblNoCitas = findViewById(R.id.lblNoCitas);

        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u != null) lblNombre.setText(u.nombre);

        rvEsp.setLayoutManager(new GridLayoutManager(this, 3));
        adEsp = new EspecialidadAdapter(listaEsp, esp -> {
            Intent i = new Intent(this, BuscarDoctorActivity.class);
            i.putExtra("idEspecialidad", esp.idEspecialidad);
            i.putExtra("nombreEsp", esp.nombre);
            startActivity(i);
        });
        rvEsp.setAdapter(adEsp);

        rvCitas.setLayoutManager(new LinearLayoutManager(this));
        adCita = new CitaAdapter(listaCitas, c -> {
            // si quieres meter detalle de cita lo haces aqui
        });
        rvCitas.setAdapter(adCita);

        findViewById(R.id.btnPerfil).setOnClickListener(v ->
                startActivity(new Intent(this, PerfilUsuarioActivity.class)));
        findViewById(R.id.cajaBuscar).setOnClickListener(v ->
                startActivity(new Intent(this, BuscarDoctorActivity.class)));
        findViewById(R.id.cardHistorial).setOnClickListener(v ->
                startActivity(new Intent(this, HistorialActivity.class)));
        findViewById(R.id.cardFaq).setOnClickListener(v ->
                startActivity(new Intent(this, FaqActivity.class)));

        cargarEspecialidades();
    }

    @Override protected void onResume(){
        super.onResume();
        cargarMisCitas();
    }

    void cargarEspecialidades(){
        ApiCliente.api().especialidades().enqueue(new Callback<List<Especialidad>>() {
            @Override public void onResponse(Call<List<Especialidad>> call, Response<List<Especialidad>> r) {
                listaEsp.clear();
                if(r.isSuccessful() && r.body() != null){
                    listaEsp.addAll(r.body());
                } else {
                    // por si no hay api, mandamos unas de prueba para no quedar en blanco
                    listaEsp.addAll(dummyEsp());
                }
                adEsp.notifyDataSetChanged();
            }
            @Override public void onFailure(Call<List<Especialidad>> call, Throwable t) {
                listaEsp.clear();
                listaEsp.addAll(dummyEsp());
                adEsp.notifyDataSetChanged();
            }
        });
    }

    void cargarMisCitas(){
        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u == null) return;
        ApiCliente.api().misCitas(u.idUsuario).enqueue(new Callback<List<Cita>>() {
            @Override public void onResponse(Call<List<Cita>> call, Response<List<Cita>> r) {
                listaCitas.clear();
                if(r.isSuccessful() && r.body() != null){
                    for(Cita c : r.body()){
                        // solo pendientes/confirmadas pa "proximas"
                        if("pendiente".equalsIgnoreCase(c.estado) || "confirmada".equalsIgnoreCase(c.estado)){
                            listaCitas.add(c);
                        }
                    }
                }
                lblNoCitas.setVisibility(listaCitas.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
                adCita.notifyDataSetChanged();
            }
            @Override public void onFailure(Call<List<Cita>> call, Throwable t) {
                lblNoCitas.setVisibility(android.view.View.VISIBLE);
            }
        });
    }

    List<Especialidad> dummyEsp(){
        List<Especialidad> l = new ArrayList<>();
        String[] nombres = {"Medicina general","Pediatría","Dermatología","Cardiología","Odontología","Ginecología","Psicología","Nutrición","Oftalmología"};
        for(int i=0;i<nombres.length;i++){
            Especialidad e = new Especialidad();
            e.idEspecialidad = "esp_"+i;
            e.nombre = nombres[i];
            l.add(e);
        }
        return l;
    }
}