package com.example.ataverna;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ataverna.databinding.ActivityImageLoaderBinding;
import com.example.ataverna.databinding.TelaRanking1Binding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TelaRanking extends BaseMainActivity {
    TextView lblAlbumNome;
    TelaRanking1Binding binding;
    Handler mainHandler = new Handler();
    ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_ranking_1);

        binding = TelaRanking1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Album albumList = null;

        lblAlbumNome = findViewById(R.id.lblAlbumNome);
        btnVoltar = findViewById(R.id.btnVoltarRanking);

        if (getIntent().hasExtra(TelaPesquisa.NEXT_SCREEN)) {
            albumList = (Album) getIntent().getSerializableExtra(TelaPesquisa.NEXT_SCREEN);
        }

        if (albumList != null) {
            lblAlbumNome.setText(albumList.getNome());
            new FetchImage(albumList.getUrl()).start();
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaRanking.this, TelaPesquisa.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onViewCreated() {
        // Inicialize as visualizações aqui, se necessário
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
