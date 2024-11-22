package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class TelaUsuario extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario);

        Button btnSair = findViewById(R.id.btnSair);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        TextView txtUsuario = findViewById(R.id.txtUsuario);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("Usuario");

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());
        usuarioLogado.child("usuario").addValueEventListener(new ValueEventListener() {
            String usuario = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue().toString();
                // Mostra o nome do usuário
                txtUsuario.setText("Bem-vindo, " + usuario.toUpperCase() +"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSair.setOnClickListener(View -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(TelaUsuario.this, TelaLogin.class);

            startActivity(intent);
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaUsuario.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });
    }
}
