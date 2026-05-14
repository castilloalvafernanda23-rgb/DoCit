package com.cbtis226.docit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.adapters.DoctorAdapter;
import com.cbtis226.docit.model.Doctor;
import com.cbtis226.docit.net.ApiCliente;
import java.util.*;
import retrofit2.*;

public class BuscarDoctorActivity extends AppCompatActivity {

    RecyclerView rv;
    EditText txtBuscar;
    TextView lblTitulo;
    List<Doctor> data = new ArrayList<>();
    DoctorAdapter ad;
    String idEsp;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_buscar_doctor);

        rv = findViewById(R.id.rvDocs);
        txtBuscar = findViewById(R.id.txtBuscar);
        lblTitulo = findViewById(R.id.lblTitulo);
        findViewById(R.id.btnRegresar).setOnClickListener(v -> finish());

        idEsp = getIntent().getStringExtra("idEspecialidad");
        String nombreEsp = getIntent().getStringExtra("nombreEsp");
        if(nombreEsp != null) lblTitulo.setText(nombreEsp);

        rv.setLayoutManager(new LinearLayoutManager(this));
        ad = new DoctorAdapter(data, d -> {
            Intent i = new Intent(this, PerfilDoctorActivity.class);
            i.putExtra("idDoctor", d.idDoctor);
            startActivity(i);
        });
        rv.setAdapter(ad);

        txtBuscar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int x, int c) {}
            public void onTextChanged(CharSequence s, int a, int x, int c) {}
            public void afterTextChanged(Editable s) { buscar(s.toString()); }
        });

        buscar(""); // primera carga
    }

    void buscar(String q){
        ApiCliente.api().doctores(idEsp, q, null).enqueue(new Callback<List<Doctor>>() {
            @Override public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> r) {
                data.clear();
                if(r.isSuccessful() && r.body() != null){
                    data.addAll(r.body());
                } else {
                    data.addAll(doctoresDummy());
                }
                ad.notifyDataSetChanged();
            }
            @Override public void onFailure(Call<List<Doctor>> call, Throwable t) {
                data.clear();
                data.addAll(doctoresDummy());
                ad.notifyDataSetChanged();
            }
        });
    }

    List<Doctor> doctoresDummy(){
        // unos doctores de mentirillas pa cuando la api esta caida
        List<Doctor> l = new ArrayList<>();
        String[] nombres = {"Juan","María","Pedro","Lupita","Carlos","Andrea"};
        String[] aps = {"Hernández","Lopez","Ramírez","Pérez","Sánchez","García"};
        for(int i=0;i<nombres.length;i++){
            Doctor d = new Doctor();
            d.idDoctor = "doc_"+i;
            d.nombre = nombres[i];
            d.apellidoPaterno = aps[i];
            d.apellidoMaterno = "";
            d.promedioCalificacion = 4.0 + Math.random();
            d.descripcion = "Médico con varios años de experiencia.";
            l.add(d);
        }
        return l;
    }
}