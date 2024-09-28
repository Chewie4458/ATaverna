package com.example.ataverna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AlbumAdapter extends FirebaseRecyclerAdapter<
        Album, AlbumAdapter.albunsViewholder> {

    public AlbumAdapter(
            @NonNull FirebaseRecyclerOptions<Album> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull albunsViewholder holder,
                     int position, @NonNull Album model)
    {
        holder.nome.setText(model.getNome());

        holder.artista.setText(model.getArtista());
    }

    @NonNull
    @Override
    public albunsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album, parent, false);
        return new AlbumAdapter.albunsViewholder(view);
    }

    class albunsViewholder
            extends RecyclerView.ViewHolder {
        TextView nome, artista;
        public albunsViewholder(@NonNull View itemView)
        {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            artista = itemView.findViewById(R.id.artista);
        }
    }
}
