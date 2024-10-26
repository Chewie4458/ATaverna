package com.example.ataverna;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TelaRegistro extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_registro);

        // Define as variáveis dos botões
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVoltar = findViewById(R.id.btn_voltar);

        // Define as variáveis dos campos
        EditText edtLogin = findViewById(R.id.edtLogin);
        EditText edtUsuario = findViewById(R.id.edtUsuario);
        EditText edtSenha = findViewById(R.id.edtSenha);
        EditText edtSenha2 = findViewById(R.id.edtSenha2);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("Usuario");

        // Botão Registar - vai para tela de login
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recupera os valores digitados
                String login   = edtLogin.getText().toString().trim();
                String usuario = edtUsuario.getText().toString().trim();
                String senha   = edtSenha.getText().toString().trim();
                String senha2  = edtSenha2.getText().toString().trim();

                /**** Validações de registro ****/
                // Verifica se o campo Login está preenchido
                if (login.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite o E-mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se o campo Senha está preenchido
                if (senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite a Senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se a senha tem no mínimo 6 caracteres
                if (senha.length() < 6) {
                    Toast.makeText(getApplicationContext(), "A senha deve conter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se o campo Repita a Senha está preenchido
                if (senha2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Repita a Senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se os campos de senha estão iguais
                if (!senha.equals(senha2)) {
                    Toast.makeText(getApplicationContext(), "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se o e-mail é válido
                if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                    Toast.makeText(getApplicationContext(), "Digite um E-mail válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cadastro de usuário (Firebase)
                auth.createUserWithEmailAndPassword(login, senha).addOnCompleteListener(
                        TelaRegistro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Salva o usuário no banco
                                    usuarioBD.child(auth.getUid()).child("usuario").setValue(usuario);

                                    Toast.makeText(getApplicationContext(), "Bem-vindo, " + usuario.toUpperCase() +"!", Toast.LENGTH_SHORT).show();

                                    // Volta para tela de registro
                                    Intent intent = new Intent(TelaRegistro.this, TelaPrincipal.class);
                                    startActivity(intent);
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {
                                        Toast.makeText(getApplicationContext(), "A senha deve conter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(getApplicationContext(), "Digite um E-mail válido", Toast.LENGTH_SHORT).show();
                                    } catch(FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(getApplicationContext(), "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
                                    } catch(Exception e) {
                                        Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_SHORT).show();
                                        Log.e("ERRO", "Ocorreram os seguintes problemas: " + task.getException());
                                    }
                                }
                            }
                        }
                );
            }
        });

        // Botão Voltar - vai para tela de login
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaRegistro.this, TelaLogin.class);
                startActivity(intent);
            }
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
