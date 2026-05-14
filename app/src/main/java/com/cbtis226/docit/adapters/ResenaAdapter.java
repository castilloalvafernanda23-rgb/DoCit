package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Resena;
import java.util.List;

public class ResenaAdapter extends RecyclerView.Adapter<ResenaAdapter.VH> {
    List<Resena> data;
    public ResenaAdapter(List<Resena> d){ data=d; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_resena, p, false));
    }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Resena r = data.get(pos);
        h.tUser.setText(r.usuario != null ? r.usuario.nombreCompleto() : "Anónimo");
        h.tCalif.setText(String.format("%.1f ★", r.calificacion == null ? 0 : r.calificacion));
        h.tComent.setText(r.comentario);
        h.tFecha.setText(r.fechaCreacion);
    }
    @Override public int getItemCount(){ return data == null ? 0 : data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tUser, tCalif, tComent, tFecha;
        VH(View v){
            super(v);
            tUser = v.findViewById(R.id.tUser);
            tCalif = v.findViewById(R.id.tCalifR);
            tComent = v.findViewById(R.id.tComent);
            tFecha = v.findViewById(R.id.tFechaR);
        }
    }
}