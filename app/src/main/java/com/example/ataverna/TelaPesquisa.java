package com.example.ataverna;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.google.firebase.database.DatabaseReference;


public class TelaPesquisa extends BaseMainActivity {
    private RecyclerView recResult;
    AlbumAdapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_tela_pesquisa);

        mbase = FirebaseDatabase.getInstance().getReference();

        recResult = findViewById(R.id.recResult);

        // Mostra Recycler com linear layout
        recResult.setLayoutManager(
                new LinearLayoutManager(this));

        // Query
        FirebaseRecyclerOptions<Album> options
                = new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(mbase.child("AlbumTeste"), Album.class)
                .build();

        // Inicializa o Adapter
        adapter = new AlbumAdapter(options);
        // Conecta o Adapter com o Recycler
        recResult.setAdapter(adapter);
    }

    // Função para começar a recuperar os dados
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Função para parar de recuperar os dados
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
