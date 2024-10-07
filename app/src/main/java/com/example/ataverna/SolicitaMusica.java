package com.example.ataverna;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.sql.Timestamp;

public class SolicitaMusica extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solicita_musica);

        Button btnSolicita = findViewById(R.id.btnSolicita);
        EditText txtNomeSolicitado = findViewById(R.id.txtNomeSolicitado);

        DatabaseReference albumSolicitado = referencia.child("AlbumSolicitado");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSolicita.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String texto = txtNomeSolicitado.getText().toString();
                albumSolicitado.child(timestamp.getTime() + "").child("nome").setValue(texto);
                Toast.makeText(getApplicationContext(), "Solicitação feita com sucesso!", Toast.LENGTH_SHORT).show();
                txtNomeSolicitado.setText("");
            }
        });
    }
}
