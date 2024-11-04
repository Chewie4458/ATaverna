package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

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
    ImageButton btnPesquisar;

    String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_tela_pesquisa);

        mbase = FirebaseDatabase.getInstance().getReference("Album");

        recResult = findViewById(R.id.recResult);
        btnPesquisar = findViewById(R.id.btnPesquisa);

        ImageButton btnPesquisa = findViewById(R.id.btnPesquisa);
        EditText edtPesquisa = findViewById(R.id.edtPesquisa);

        // Mostra Recycler com linear layout
        recResult.setLayoutManager(
                new WrapContentLinearLayoutManager(TelaPesquisa.this, LinearLayoutManager.VERTICAL, false));

        // Monta a queryString (valor da pesquisa)
        queryString = String.valueOf(edtPesquisa.getText());

        // Query
        FirebaseRecyclerOptions<Album> options
                = new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(mbase.orderByChild("nome").startAt(queryString)
                .endAt(queryString + "\uf8ff"), Album.class)
                .build();

        // Inicializa o Adapter
        adapter = new AlbumAdapter(options);
        // Conecta o Adapter com o Recycler
        recResult.setAdapter(adapter);

        adapter.setOnClickListener(new AlbumAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Album model) {
                Intent intent = new Intent(TelaPesquisa.this, TelaRanking.class);
                intent.putExtra(NEXT_SCREEN, model);
                startActivity(intent);
            }
        });
    }

    public static final String NEXT_SCREEN = "tela_ranking_1";

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

    public void pesquisar(View v)
    {
        EditText edtPesquisa = findViewById(R.id.edtPesquisa);
        // Monta a queryString (valor da pesquisa)
        queryString = String.valueOf(edtPesquisa.getText());

        // Query
        FirebaseRecyclerOptions<Album> options
                = new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(mbase.orderByChild("nome").startAt(queryString)
                        .endAt(queryString + "\uf8ff"), Album.class)
                .build();

        // Inicializa o Adapter
        adapter = new AlbumAdapter(options);
        // Conecta o Adapter com o Recycler
        recResult.setAdapter(adapter);
        onStart();

        adapter.setOnClickListener(new AlbumAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Album model) {
                Intent intent = new Intent(TelaPesquisa.this, TelaRanking.class);
                intent.putExtra(NEXT_SCREEN, model);
                startActivity(intent);
            }
        });
    }

    public void cadastrar(View v) {
        Intent intent = new Intent(TelaPesquisa.this, SolicitaMusica.class);
        startActivity(intent);
    }

    public void voltar(View v) {
        Intent intent = new Intent(TelaPesquisa.this, TelaPrincipal.class);
        startActivity(intent);
    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
