package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class TelaPrincipal extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_principal);

        ImageButton btnUsuario = findViewById(R.id.btnPerfilTelaPrincipal);

        btnUsuario.setOnClickListener(View -> {
            Intent intent = new Intent(TelaPrincipal.this, TelaUsuario.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
