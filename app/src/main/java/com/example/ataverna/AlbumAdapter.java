package com.example.ataverna;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.example.ataverna.databinding.AlbumBinding;
import com.example.ataverna.Album;
import com.example.ataverna.TelaPesquisa;


public class AlbumAdapter extends FirebaseRecyclerAdapter<
        Album, AlbumAdapter.albunsViewholder> {

    private OnClickListener onClickListener;

    public AlbumAdapter(
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
        AlbumBinding binding = AlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album, parent, false);
        return new AlbumAdapter.albunsViewholder(view, binding);
    }

    class albunsViewholder
            extends RecyclerView.ViewHolder {
        AlbumBinding binding;
        TextView nome, artista, url;
        public albunsViewholder(@NonNull View itemView, AlbumBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Album model, OnClickListener onClickListener) {
            binding.nome.setText(model.getNome());
            binding.artista.setText(model.getArtista());
            binding.url.setText(model.getUrl());

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
