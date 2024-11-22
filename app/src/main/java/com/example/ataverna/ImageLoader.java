package com.example.ataverna;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ataverna.databinding.ActivityImageLoaderBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ImageLoader extends AppCompatActivity {
    private RecyclerView recResult;
    static ActivityImageLoaderBinding binding;
    static Handler mainHandler = new Handler();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    AlbumCapasAdapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        binding = ActivityImageLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mbase = FirebaseDatabase.getInstance().getReference("Album");
        recResult = findViewById(R.id.rvAlbuns);

        final String[] url = {"https://i.scdn.co/image/ab67616d0000b273ed801e58a9ababdea6ac7ce4"};

        //recResult.setLayoutManager(new GridLayoutManager(this, 3));
        recResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Query
        FirebaseRecyclerOptions<Album> options
                = new FirebaseRecyclerOptions.Builder<Album>()
                .setQuery(mbase.orderByChild("nome"), Album.class)
                .build();

        // Inicializa o Adapter
        adapter = new AlbumCapasAdapter(options);
        // Conecta o Adapter com o Recycler
        recResult.setAdapter(adapter);
    }

    static class FetchImage extends Thread{
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
                    //binding.imgTeste.setImageBitmap(bitmap);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

}
