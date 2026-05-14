package com.cbtis226.docit.adapters;

import android.view.*;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.cbtis226.docit.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImagenSliderAdapter extends SliderViewAdapter<ImagenSliderAdapter.H> {

    List<String> urls;
    public ImagenSliderAdapter(List<String> u){ urls = u; }

    @Override public H onCreateViewHolder(ViewGroup p) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_slide, null);
        return new H(v);
    }
    @Override public void onBindViewHolder(@NonNull H h, int pos) {
        Picasso.get().load(urls.get(pos)).placeholder(R.color.grisClaro).into(h.img);
    }
    @Override public int getCount(){ return urls == null ? 0 : urls.size(); }

    static class H extends SliderViewAdapter.ViewHolder {
        ImageView img;
        H(View v){ super(v); img = v.findViewById(R.id.imgSlide); }
    }
}