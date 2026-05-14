package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.HorarioDisponible;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.VH> {
    public interface OnTap { void tap(HorarioDisponible h); }
    List<HorarioDisponible> data;
    OnTap cb;
    HorarioDisponible sel;

    public HorarioAdapter(List<HorarioDisponible> d, OnTap c){ data=d; cb=c; }

    public void setSeleccion(HorarioDisponible h){
        sel = h; notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_horario, p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        HorarioDisponible x = data.get(pos);
        h.t.setText(x.horaInicio);
        boolean elegido = sel != null && sel.idHorario != null && sel.idHorario.equals(x.idHorario);
        h.t.setBackgroundResource(elegido ? R.drawable.bg_chip_sel : R.drawable.bg_chip_nosel);
        h.t.setTextColor(h.itemView.getContext().getResources().getColor(
                elegido ? R.color.blanco : R.color.rojoDocit));
        h.itemView.setOnClickListener(v -> cb.tap(x));
    }
    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView t;
        VH(View v){ super(v); t = v.findViewById(R.id.tHora); }
    }
}