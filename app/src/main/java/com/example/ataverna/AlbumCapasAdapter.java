package com.example.ataverna;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ataverna.databinding.AlbumCapasBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AlbumCapasAdapter extends FirebaseRecyclerAdapter<
        Album, AlbumCapasAdapter.albunsViewholder> {

    AlbumCapasBinding binding;
    Handler mainHandler = new Handler();
    private OnClickListener onClickListener;

    public AlbumCapasAdapter(
            @NonNull FirebaseRecyclerOptions<Album> options)
    {
        super(options);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position, Album model);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull albunsViewholder holder,
                     int position, @NonNull Album model)
    {
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, model);
            }
        });

        holder.bind(model, onClickListener);
    }

    @NonNull
    @Override
    public albunsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        AlbumCapasBinding binding = AlbumCapasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new albunsViewholder(binding);
    }

    class albunsViewholder
            extends RecyclerView.ViewHolder {
        AlbumCapasBinding binding;
        public albunsViewholder(AlbumCapasBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Album model, OnClickListener onClickListener) {
            // Carregar a imagem com Glide
            Glide.with(binding.imgCapa.getContext())
                    .load(model.getUrl())
                    .into(binding.imgCapa);

            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onClick(position, model);
                    }
                }
            });
        }
    }
}
