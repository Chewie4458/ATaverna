package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TelaPrincipal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RankersAdapter adapter; // Adaptador personalizado para exibir os dados do modelo Rankers
    private DatabaseReference mDatabase; // Referência do Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tela_principal); // Defina o layout para esta activity

        ImageButton btnPesquisa = findViewById(R.id.btnToPesquisa);
        ImageButton btnPerfilTelaPrincipal = findViewById(R.id.btnPerfilTelaPrincipal);

        // Inicializa a referência ao nó do banco de dados do Firebase onde os dados do Rankers estão armazenados
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rankers");

        // Configura o RecyclerView
        recyclerView = findViewById(R.id.rankers_recicla);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura as opções para o FirebaseRecyclerAdapter
        FirebaseRecyclerOptions<Rankers> options = new FirebaseRecyclerOptions.Builder<Rankers>()
                .setQuery(mDatabase.orderByChild("hora"), Rankers.class)
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
    }

    // Para a escuta do adaptador quando a Activity é pausada ou parada
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
