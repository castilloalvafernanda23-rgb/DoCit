package com.cbtis226.docit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.adapters.*;
import com.cbtis226.docit.model.*;
import com.cbtis226.docit.net.ApiCliente;
import com.smarteist.autoimageslider.SliderView;
import java.util.*;
import retrofit2.*;

public class PerfilDoctorActivity extends AppCompatActivity {

    String idDoc;
    Doctor doc;
    TextView lblNombre, lblEsp, lblCalif, lblDesc;
    SliderView slider;
    RecyclerView rvServ, rvRes;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_perfil_doctor);

        idDoc = getIntent().getStringExtra("idDoctor");
        lblNombre = findViewById(R.id.lblNombreDoc);
        lblEsp = findViewById(R.id.lblEsp);
        lblCalif = findViewById(R.id.lblCalif);
        lblDesc = findViewById(R.id.lblDesc);
        slider = findViewById(R.id.imageSlider);
        rvServ = findViewById(R.id.rvServicios);
        rvRes = findViewById(R.id.rvResenas);

        rvServ.setLayoutManager(new LinearLayoutManager(this));
        rvRes.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnAgendar).setOnClickListener(v -> {
            if(doc == null) return;
            Intent i = new Intent(this, CrearCitaActivity.class);
            i.putExtra("idDoctor", doc.idDoctor);
            startActivity(i);
        });
        findViewById(R.id.btnWhats).setOnClickListener(v -> {
            if(doc == null || doc.whatsapp == null) return;
            try {
                Intent w = new Intent(Intent.ACTION_VIEW);
                w.setData(Uri.parse("https://wa.me/" + doc.whatsapp.replaceAll("\\D","")));
                startActivity(w);
            } catch(Exception e){ }
        });
        findViewById(R.id.btnLlamar).setOnClickListener(v -> {
            if(doc == null || doc.telefono == null) return;
            Intent c = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + doc.telefono));
            startActivity(c);
        });

        cargar();
    }

    void cargar(){
        ApiCliente.api().doctor(idDoc).enqueue(new Callback<Doctor>() {
            @Override public void onResponse(Call<Doctor> call, Response<Doctor> r) {
                if(r.isSuccessful() && r.body() != null){
                    doc = r.body();
                    pintar();
                } else {
                    cargarDummy();
                }
            }
            @Override public void onFailure(Call<Doctor> call, Throwable t) {
                cargarDummy();
            }
        });
    }

    void cargarDummy(){
        doc = new Doctor();
        doc.idDoctor = idDoc != null ? idDoc : "doc_demo";
        doc.nombre = "Roberto";
        doc.apellidoPaterno = "Mendoza";
        doc.apellidoMaterno = "Luna";
        doc.descripcion = "Médico cirujano egresado de la UNAM con 12 años de experiencia. Atiende citas particulares y de emergencia.";
        doc.promedioCalificacion = 4.7;
        Especialidad e = new Especialidad();
        e.nombre = "Medicina general";
        doc.especialidad = e;
        // unas imagenes dummy
        doc.imagenes = new ArrayList<>();
        String[] urls = {
                "https://picsum.photos/seed/d1/600/400",
                "https://picsum.photos/seed/d2/600/400",
                "https://picsum.photos/seed/d3/600/400"
        };
        for(String u : urls){
            Imagen im = new Imagen();
            im.urlImagen = u;
            doc.imagenes.add(im);
        }
        // servicios
        doc.servicios = new ArrayList<>();
        String[] ns = {"Consulta general","Receta médica","Chequeo anual"};
        double[] ps = {350, 200, 800};
        for(int i=0;i<ns.length;i++){
            Servicio s = new Servicio();
            s.idServicio = "s"+i;
            s.nombre = ns[i];
            s.precio = ps[i];
            s.duracion = 30;
            s.descripcion = "Servicio profesional con atención personalizada.";
            doc.servicios.add(s);
        }
        doc.resenas = new ArrayList<>();
        pintar();
    }

    void pintar(){
        lblNombre.setText(doc.nombreDr());
        lblEsp.setText(doc.especialidad != null ? doc.especialidad.nombre : "");
        lblCalif.setText(String.format("%.1f ★ (%d reseñas)",
                doc.promedioCalificacion == null ? 0 : doc.promedioCalificacion,
                doc.resenas == null ? 0 : doc.resenas.size()));
        lblDesc.setText(doc.descripcion);

        List<String> imgs = new ArrayList<>();
        if(doc.imagenes != null){
            for(Imagen im : doc.imagenes) imgs.add(im.urlImagen);
        }
        slider.setSliderAdapter(new ImagenSliderAdapter(imgs));

        if(doc.servicios != null){
            rvServ.setAdapter(new ServicioAdapter(doc.servicios));
        }
        if(doc.resenas != null){
            rvRes.setAdapter(new ResenaAdapter(doc.resenas));
        }
    }
}