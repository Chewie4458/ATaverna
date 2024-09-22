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

import com.example.ataverna.databinding.ActivityImageLoaderBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ImageLoader extends AppCompatActivity {
    ActivityImageLoaderBinding binding;
    Handler mainHandler = new Handler();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        binding = ActivityImageLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //DatabaseReference album = referencia.child("Album").child("0DFYbYCcHCEJPcN1hODG6K").child("-O6x9VcQnRLr_VxqAV5e").child("capa").child("url");

        String queryText = "American Idiot";

//        DatabaseReference albumTeste = referencia.child("AlbumTeste").child(queryText).child("capa").child("url");
//
        final String[] url = {"https://i.scdn.co/image/ab67616d0000b273ed801e58a9ababdea6ac7ce4"};
//
//        albumTeste.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                url[0] = snapshot.getValue().toString();
//
//                new FetchImage(url[0]).start();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                url[0] = error.toString();
//            }
//        });

        DatabaseReference albumTeste = referencia.child("AlbumTeste");

        Query qryTeste = albumTeste.startAt(queryText)
                .endAt(queryText+"\uf8ff");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
//               url[0] = snapshot.getValue().toString();
//
//               new FetchImage(url[0]).start();
                System.out.println("added");
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
//                url[0] = snapshot.getValue().toString();
//
//                new FetchImage(url[0]).start();
                System.out.println("changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        qryTeste.addChildEventListener(childEventListener);
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
                    binding.imgTeste.setImageBitmap(bitmap);
                }
            });
        }
    }
}