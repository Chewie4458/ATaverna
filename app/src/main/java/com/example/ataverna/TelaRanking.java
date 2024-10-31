package com.example.ataverna;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ataverna.databinding.ActivityImageLoaderBinding;
import com.example.ataverna.databinding.TelaRanking1Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;

public class TelaRanking extends BaseMainActivity {
    TelaRanking1Binding binding;
    Handler mainHandler = new Handler();

    TextView lblAlbumNome;
    ImageButton btnVoltar;
    EditText edtComentario;

    public String nota = "0";
    public String comentario = "";
    public String usuario = "";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_ranking_1);

        binding = TelaRanking1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Album albumList = null;

        lblAlbumNome = findViewById(R.id.lblAlbumNome);
        btnVoltar = findViewById(R.id.btnVoltarRanking);
        edtComentario = findViewById(R.id.edtComentario);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("Usuario");
        DatabaseReference ranker = referencia.child("Rankers");

        if (getIntent().hasExtra(TelaPesquisa.NEXT_SCREEN)) {
            albumList = (Album) getIntent().getSerializableExtra(TelaPesquisa.NEXT_SCREEN);
        }

        if (albumList != null) {
            lblAlbumNome.setText(albumList.getNome());
            new FetchImage(albumList.getUrl()).start();
        }

        Album finalAlbumList = albumList;
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupera o comentário do usuário
                comentario = edtComentario.getText().toString();

                // Salva a nota / comentário do usuário
                usuarioBD.child(auth.getUid()).child(finalAlbumList.getNome())
                        .child("nota").setValue(nota);
                usuarioBD.child(auth.getUid()).child(finalAlbumList.getNome())
                        .child("comentario").setValue(comentario);

                // Salva um registro de ranking
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                ranker.child(timestamp.getTime() + "").child("nome").setValue(usuario);
                ranker.child(timestamp.getTime() + "").child("album").setValue(finalAlbumList.getNome());
                ranker.child(timestamp.getTime() + "").child("nota").setValue(nota);
                ranker.child(timestamp.getTime() + "").child("comentario").setValue(comentario);

                Intent intent = new Intent(TelaRanking.this, TelaPesquisa.class);
                startActivity(intent);
            }
        });

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());
        usuarioLogado.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usuarioLogado.child(finalAlbumList.getNome()).child("nota").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nota = snapshot.getValue().toString(); // se não tem o registro para o album da erro

                switch (nota) {
                    case "1":
                        btnEstrelaRanking_1(null);
                        break;
                    case "2":
                        btnEstrelaRanking_2(null);
                        break;
                    case "3":
                        btnEstrelaRanking_3(null);
                        break;
                    case "4":
                        btnEstrelaRanking_4(null);
                        break;
                    case "5":
                        btnEstrelaRanking_5(null);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usuarioLogado.child(finalAlbumList.getNome()).child("comentario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comentario = snapshot.getValue().toString(); // se não tem o registro para o album da erro

                edtComentario.setText(comentario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onViewCreated() {
        // Inicialize as visualizações aqui, se necessário
    }

    public void vibra(long tempo) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(tempo);
    }

    // Estrelas
    public void btnEstrelaRanking_1(View view) {
        ImageButton btnEstrela1 = findViewById(R.id.btnEstrelaRanking_1);
        ImageButton btnEstrela2 = findViewById(R.id.btnEstrelaRanking_2);
        ImageButton btnEstrela3 = findViewById(R.id.btnEstrelaRanking_3);
        ImageButton btnEstrela4 = findViewById(R.id.btnEstrelaRanking_4);
        ImageButton btnEstrela5 = findViewById(R.id.btnEstrelaRanking_5);

        btnEstrela1.setSelected(!btnEstrela1.isSelected());

        if (btnEstrela1.isSelected()) {
            btnEstrela1.setImageResource(R.drawable.ic_star_preenchido);
        }
        else {
            btnEstrela2.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela3.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela4.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela5.setImageResource(R.drawable.ic_star_outline1);

            btnEstrela2.setSelected(false);
            btnEstrela3.setSelected(false);
            btnEstrela4.setSelected(false);
            btnEstrela5.setSelected(false);
        }

        nota = "1";
        vibra(100);
    }

    public void btnEstrelaRanking_2(View view) {
        ImageButton btnEstrela1 = findViewById(R.id.btnEstrelaRanking_1);
        ImageButton btnEstrela2 = findViewById(R.id.btnEstrelaRanking_2);
        ImageButton btnEstrela3 = findViewById(R.id.btnEstrelaRanking_3);
        ImageButton btnEstrela4 = findViewById(R.id.btnEstrelaRanking_4);
        ImageButton btnEstrela5 = findViewById(R.id.btnEstrelaRanking_5);

        btnEstrela2.setSelected(!btnEstrela2.isSelected());

        if (btnEstrela2.isSelected()) {
            btnEstrela1.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela2.setImageResource(R.drawable.ic_star_preenchido);

            btnEstrela1.setSelected(true);
        }
        else {
            btnEstrela3.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela4.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela5.setImageResource(R.drawable.ic_star_outline1);

            btnEstrela3.setSelected(false);
            btnEstrela4.setSelected(false);
            btnEstrela5.setSelected(false);
        }

        nota = "2";
        vibra(100);
    }

    public void btnEstrelaRanking_3(View view) {
        ImageButton btnEstrela1 = findViewById(R.id.btnEstrelaRanking_1);
        ImageButton btnEstrela2 = findViewById(R.id.btnEstrelaRanking_2);
        ImageButton btnEstrela3 = findViewById(R.id.btnEstrelaRanking_3);
        ImageButton btnEstrela4 = findViewById(R.id.btnEstrelaRanking_4);
        ImageButton btnEstrela5 = findViewById(R.id.btnEstrelaRanking_5);

        btnEstrela3.setSelected(!btnEstrela3.isSelected());

        if (btnEstrela3.isSelected()) {
            btnEstrela1.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela2.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela3.setImageResource(R.drawable.ic_star_preenchido);

            btnEstrela1.setSelected(true);
            btnEstrela2.setSelected(true);
        }
        else {
            btnEstrela4.setImageResource(R.drawable.ic_star_outline1);
            btnEstrela5.setImageResource(R.drawable.ic_star_outline1);

            btnEstrela4.setSelected(false);
            btnEstrela5.setSelected(false);
        }

        nota = "3";
        vibra(100);
    }

    public void btnEstrelaRanking_4(View view) {
        ImageButton btnEstrela1 = findViewById(R.id.btnEstrelaRanking_1);
        ImageButton btnEstrela2 = findViewById(R.id.btnEstrelaRanking_2);
        ImageButton btnEstrela3 = findViewById(R.id.btnEstrelaRanking_3);
        ImageButton btnEstrela4 = findViewById(R.id.btnEstrelaRanking_4);
        ImageButton btnEstrela5 = findViewById(R.id.btnEstrelaRanking_5);

        btnEstrela4.setSelected(!btnEstrela4.isSelected());

        if (btnEstrela4.isSelected()) {
            btnEstrela1.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela2.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela3.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela4.setImageResource(R.drawable.ic_star_preenchido);

            btnEstrela1.setSelected(true);
            btnEstrela2.setSelected(true);
            btnEstrela3.setSelected(true);
        }
        else {
            btnEstrela5.setImageResource(R.drawable.ic_star_outline1);

            btnEstrela5.setSelected(false);
        }

        nota = "4";
        vibra(100);
    }

    public void btnEstrelaRanking_5(View view) {
        ImageButton btnEstrela1 = findViewById(R.id.btnEstrelaRanking_1);
        ImageButton btnEstrela2 = findViewById(R.id.btnEstrelaRanking_2);
        ImageButton btnEstrela3 = findViewById(R.id.btnEstrelaRanking_3);
        ImageButton btnEstrela4 = findViewById(R.id.btnEstrelaRanking_4);
        ImageButton btnEstrela5 = findViewById(R.id.btnEstrelaRanking_5);

        btnEstrela5.setSelected(!btnEstrela5.isSelected());

        if (btnEstrela5.isSelected()) {
            btnEstrela1.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela2.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela3.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela4.setImageResource(R.drawable.ic_star_preenchido);
            btnEstrela5.setImageResource(R.drawable.ic_star_preenchido);

            btnEstrela1.setSelected(true);
            btnEstrela2.setSelected(true);
            btnEstrela3.setSelected(true);
            btnEstrela4.setSelected(true);
        }

        nota = "5";
        vibra(100);
    }

    private int dp2px(double dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class FetchImage extends Thread{
        String URL;
        Bitmap bitmap;

        FetchImage(String URL){
            this.URL = URL;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            try {
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    binding.imgAlbumRanking.setImageBitmap(bitmap);
                }
            });
        }
    }
}
