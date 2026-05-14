package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Servicio;
import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.VH> {
    List<Servicio> data;
    public ServicioAdapter(List<Servicio> d){ data=d; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_servicio, p, false));
    }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Servicio s = data.get(pos);
        h.tNombre.setText(s.nombre);
        h.tDesc.setText(s.descripcion != null ? s.descripcion : "");
        h.tPrecio.setText(String.format("$%.0f", s.precio == null ? 0 : s.precio));
        h.tDur.setText((s.duracion == null ? 30 : s.duracion) + " min");
    }
    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tNombre, tDesc, tPrecio, tDur;
        VH(View v){
            super(v);
            tNombre = v.findViewById(R.id.tNombreServ);
            tDesc = v.findViewById(R.id.tDescServ);
            tPrecio = v.findViewById(R.id.tPrecio);
            tDur = v.findViewById(R.id.tDur);
        }
    }
}