package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Cita;
import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.VH> {

    public interface OnTap { void tap(Cita c); }

    List<Cita> data;
    OnTap cb;

    public CitaAdapter(List<Cita> d, OnTap c){ data=d; cb=c; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        View x = LayoutInflater.from(p.getContext()).inflate(R.layout.item_cita, p, false);
        return new VH(x);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Cita c = data.get(pos);
        if(c.doctor != null){
            h.tDoctor.setText(c.doctor.nombreDr());
            h.tEsp.setText(c.doctor.especialidad != null ? c.doctor.especialidad.nombre : "");
        }
        h.tFecha.setText(c.fecha + "  " + c.horaInicio);
        h.tEstado.setText(c.estado != null ? c.estado : "pendiente");

        int colorChip;
        switch(c.estado == null ? "" : c.estado.toLowerCase()){
            case "confirmada": colorChip = R.color.estadoConfirmada; break;
            case "cancelada": colorChip = R.color.estadoCancelada; break;
            case "completada": colorChip = R.color.estadoCompletada; break;
            default: colorChip = R.color.estadoPendiente;
        }
        h.tEstado.setBackgroundTintList(
                h.itemView.getResources().getColorStateList(colorChip)
        );
        h.itemView.setOnClickListener(v -> { if(cb!=null) cb.tap(c); });
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tDoctor, tEsp, tFecha, tEstado;
        VH(View v){
            super(v);
            tDoctor = v.findViewById(R.id.txtDoctor);
            tEsp = v.findViewById(R.id.txtEsp);
            tFecha = v.findViewById(R.id.txtFecha);
            tEstado = v.findViewById(R.id.txtEstado);
        }
    }
}