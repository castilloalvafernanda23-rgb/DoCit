package com.cbtis226.docit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.DoCitApp;
import com.cbtis226.docit.R;
import com.cbtis226.docit.adapters.CitaAdapter;
import com.cbtis226.docit.model.Cita;
import com.cbtis226.docit.model.Usuario;
import com.cbtis226.docit.net.ApiCliente;
import java.util.*;
import retrofit2.*;

public class HistorialActivity extends AppCompatActivity {

    List<Cita> data = new ArrayList<>();
    CitaAdapter ad;
    RecyclerView rv;
    TextView vacio;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_historial);
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
        rv = findViewById(R.id.rvHist);
        vacio = findViewById(R.id.lblVacio);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ad = new CitaAdapter(data, c -> {});
        rv.setAdapter(ad);
        cargar();
    }

    void cargar(){
        Usuario u = DoCitApp.x().usuarioLogeado;
        if(u == null) return;
        ApiCliente.api().misCitas(u.idUsuario).enqueue(new Callback<List<Cita>>() {
            @Override public void onResponse(Call<List<Cita>> call, Response<List<Cita>> r) {
                data.clear();
                if(r.isSuccessful() && r.body() != null) data.addAll(r.body());
                ad.notifyDataSetChanged();
                vacio.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
            }
            @Override public void onFailure(Call<List<Cita>> call, Throwable t) {
                vacio.setVisibility(View.VISIBLE);
            }
        });
    }
}