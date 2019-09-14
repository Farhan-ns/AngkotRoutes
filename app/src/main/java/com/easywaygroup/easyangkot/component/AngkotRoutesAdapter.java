package com.easywaygroup.easyangkot.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.angkotroutes.R;
import com.easywaygroup.easyangkot.util.Angkot;

import java.util.List;

public class AngkotRoutesAdapter extends RecyclerView.Adapter<AngkotRoutesAdapter.ViewHolder> {
    private Listener listener;
    private Resources resources;
    private Context context;
    private List<Angkot> angkotList;

    AngkotRoutesAdapter(List<Angkot> angkotList) {
        this.angkotList = angkotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        resources = context.getResources();

        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_angkot_route,
                parent,
                false
        );
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Angkot angkot = angkotList.get(position);

        TextView textView = cardView.findViewById(R.id.text_jurusan_angkot);
        textView.setText(angkot.getNamaAngkot());

        ImageView imageView = cardView.findViewById(R.id.image_angkot);
        Drawable drawable = ResourcesCompat.getDrawable(
                resources,
                resources.getIdentifier(
                        angkot.getNamaGambar(),
                        "drawable",
                        context.getPackageName()
                ),
                null
        );
        imageView.setImageDrawable(drawable);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return angkotList.size();
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
