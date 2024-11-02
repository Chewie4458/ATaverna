package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (auth.getCurrentUser() != null) {
                    // Abre direto na tela principal se tiver usuário logado
                    intent = new Intent(Splash.this, TelaPrincipal.class);
                } else {
                    // Abre na tela de login se não tiver usuário logado
                    intent = new Intent(Splash.this, TelaLogin.class);
                }

                startActivity(intent);

                finish();
            }
        }, 2000);

    }
}