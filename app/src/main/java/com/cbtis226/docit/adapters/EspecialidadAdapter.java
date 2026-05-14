package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cbtis226.docit.R;
import com.cbtis226.docit.model.Especialidad;
import java.util.List;

public class EspecialidadAdapter extends RecyclerView.Adapter<EspecialidadAdapter.VH> {

    public interface OnClick { void click(Especialidad e); }

    List<Especialidad> data;
    OnClick cb;

    public EspecialidadAdapter(List<Especialidad> d, OnClick c){ data=d; cb=c; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_especialidad, p, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Especialidad e = data.get(pos);
        h.tv.setText(e.nombre);
        // color pastel rotativo segun posicion, pa que no se vea aburrido
        int[] colores = {
                R.color.pastelAzul, R.color.pastelVerde, R.color.pastelAmarillo,
                R.color.pastelRosa, R.color.pastelMorado
        };
        h.card.setCardBackgroundColor(h.itemView.getContext().getResources().getColor(colores[pos % colores.length]));
        h.itemView.setOnClickListener(v -> cb.click(e));
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        androidx.cardview.widget.CardView card;
        VH(View v){
            super(v);
            tv = v.findViewById(R.id.txtEsp);
            card = v.findViewById(R.id.cardEsp);
        }
    }
}