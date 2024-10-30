package com.example.ataverna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


// FirebaseRecyclerAdapter is a class provided by FirebaseUI that helps adapt Firebase database content into RecyclerView
public class RankersAdapter extends FirebaseRecyclerAdapter<Rankers, RankersAdapter.RankersViewHolder> {

    // Constructor
    public RankersAdapter(@NonNull FirebaseRecyclerOptions<Rankers> options) {
        super(options);
    }

    // Binds the data from Rankers class to the view elements in ranker_item.xml
    @Override
    protected void onBindViewHolder(@NonNull RankersViewHolder holder, int position, @NonNull Rankers model) {
        holder.nome.setText(model.getNome());
        holder.comentario.setText(model.getComentario());

    }

    // Inflates the ranker_item layout for each item in RecyclerView
    @NonNull
    @Override
    public RankersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rankers, parent, false);
        return new RankersViewHolder(view);
    }

    // ViewHolder subclass to define references to each view component in ranker_item.xml
    class RankersViewHolder extends RecyclerView.ViewHolder {
        TextView nome, comentario;//, album, nota;

        public RankersViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            comentario = itemView.findViewById(R.id.comentario);
            //album = itemView.findViewById(R.id.album);
            //nota = itemView.findViewById(R.id.nota);
        }
    }
}
