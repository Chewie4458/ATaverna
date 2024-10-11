package com.example.ataverna;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.usage.NetworkStats;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TelaLogin extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        Button btnLogin = findViewById(R.id.btnLogin);
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("23455339064-k45b6nsocdhvcs4v1convrn1bvhoek61.apps.googleusercontent.com")
                //.requestIdToken(getString(R.string.app_name))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize sign in intent
                Intent intent = googleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent, RC_SIGN_IN);
            }

            @NonNull
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });

        // Initialize firebase user
        FirebaseUser firebaseUser = auth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
//            startActivity(new Intent(TelaLogin.this, TelaPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Intent intent=new Intent(TelaLogin.this, TelaPrincipal.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //GoogleSignInAccount account = task.getResult(ApiException.class);
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = signInAccountTask.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());

            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).
            addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            public void setUser(NetworkStats.Bucket user) {
                this.user = user;
            }

            private NetworkStats.Bucket user;

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // inserir usuário no banco
//                    auth.getCurrentUser();
//                    HashMap<String, Object> map = new HashMap<>();
//                    map.put("id", user.getUid());
//                    map.put("name", user.getState());
//                    map.put("profile", user.getClass().toString());

                    //database.getReference().child("users").child(String.valueOf(user.getUid())).getValue();
                    Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(TelaLogin.this, "Something must be wrong"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
