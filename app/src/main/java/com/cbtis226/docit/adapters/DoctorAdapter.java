package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Doctor;
import com.squareup.picasso.Picasso;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.VH> {

    public interface OnClick { void click(Doctor d); }
    List<Doctor> data; OnClick cb;

    public DoctorAdapter(List<Doctor> d, OnClick c){ data=d; cb=c; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_doctor, p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Doctor d = data.get(pos);
        h.tNombre.setText(d.nombreDr());
        h.tDesc.setText(d.descripcion != null ? d.descripcion : "");
        h.tEsp.setText(d.especialidad != null ? d.especialidad.nombre : "Médico");
        double cal = d.promedioCalificacion == null ? 0 : d.promedioCalificacion;
        h.tCalif.setText(String.format("%.1f ★", cal));
        if(d.fotoPerfil != null && !d.fotoPerfil.isEmpty()){
            Picasso.get().load(d.fotoPerfil).placeholder(R.drawable.ic_medico).into(h.img);
        } else {
            h.img.setImageResource(R.drawable.ic_medico);
        }
        h.itemView.setOnClickListener(v -> cb.click(d));
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tNombre, tEsp, tDesc, tCalif;
        ImageView img;
        VH(View v){
            super(v);
            tNombre = v.findViewById(R.id.tNombre);
            tEsp = v.findViewById(R.id.tEsp);
            tDesc = v.findViewById(R.id.tDesc);
            tCalif = v.findViewById(R.id.tCalif);
            img = v.findViewById(R.id.imgDoc);
        }
    }
}