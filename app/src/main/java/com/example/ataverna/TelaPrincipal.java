package com.example.ataverna;

import static com.example.ataverna.TelaPesquisa.NEXT_SCREEN;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ataverna.databinding.ActivityImageLoaderBinding;
import com.example.ataverna.databinding.TelaPrincipalBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TelaPrincipal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RankersAdapter adapter; // Adaptador personalizado para exibir os dados do modelo Rankers
    private DatabaseReference mDatabase; // Referência do Firebase Realtime Database
    // Visualização Albuns
    private RecyclerView recResult;
    static TelaPrincipalBinding binding;
    AlbumCapasAdapter adapterAlbuns;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tela_principal); // Defina o layout para esta activity

        binding = TelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton btnPesquisa = findViewById(R.id.btnToPesquisa);
        ImageButton btnPerfilTelaPrincipal = findViewById(R.id.btnPerfilTelaPrincipal);

        // Visualização dos álbuns
        mbase = FirebaseDatabase.getInstance().getReference("Album");
        recResult = findViewById(R.id.rvAlbuns);

        // Inicializa a referência ao nó do banco de dados do Firebase onde os dados do Rankers estão armazenados
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rankers");

        // Configura o RecyclerView (Albuns)
        recResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Query
        FirebaseRecyclerOptions<Album> optionsAlbuns
                = new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(mbase.orderByChild("reviews").limitToFirst(10), Album.class)
                .build();

        // Inicializa o Adapter
        adapterAlbuns = new AlbumCapasAdapter(optionsAlbuns);
        // Conecta o Adapter com o Recycler
        recResult.setAdapter(adapterAlbuns);

        adapterAlbuns.setOnClickListener(new AlbumCapasAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Album model) {
                Intent intent = new Intent(TelaPrincipal.this, TelaRanking.class);
                intent.putExtra(NEXT_SCREEN, model);
                startActivity(intent);
            }
        });

        // Configura o RecyclerView (Rankers)
        recyclerView = findViewById(R.id.rankers_recicla);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura as opções para o FirebaseRecyclerAdapter
        FirebaseRecyclerOptions<Rankers> options = new FirebaseRecyclerOptions.Builder<Rankers>()
                .setQuery(mDatabase.orderByChild("hora").limitToFirst(15), Rankers.class)
                .build();

        // Instancia o adaptador e o conecta ao RecyclerView
        adapter = new RankersAdapter(options);
        recyclerView.setAdapter(adapter);

        btnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, TelaPesquisa.class);
                startActivity(intent);
            }
        });

        btnPerfilTelaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, TelaUsuario.class);
                startActivity(intent);
            }
        });
    }

    // Inicia a escuta do adaptador quando a Activity é iniciada
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapterAlbuns.startListening();
    }

    // Para a escuta do adaptador quando a Activity é pausada ou parada
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapterAlbuns.stopListening();
    }
}
