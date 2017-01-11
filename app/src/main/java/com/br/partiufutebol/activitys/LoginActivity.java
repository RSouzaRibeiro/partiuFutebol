package com.br.partiufutebol.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.partiufutebol.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    Activity activity = this;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private TextView txtCadastro;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        InitComponentes();


        loginButton.setReadPermissions(Arrays.asList("email", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                compenentesViewGone();
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                naoLogou();
            }

            @Override
            public void onError(FacebookException error) {
                loginError();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if(usuario != null){
                    logado();
                }
            }
        };

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);

            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

             firebaseAuth.signInWithEmailAndPassword(email, senha)
                                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(Task<AuthResult> task) {

                                                    if(!task.isSuccessful()){
                                                        Toast.makeText(LoginActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                                                    }else{

                                                        compenentesViewGone();
                                                        logado();
                                                    }
                                                }
                                            });


            }
        });




    }

    private void compenentesViewGone() {
        loginButton.setVisibility(View.GONE);
        edtEmail.setVisibility(View.GONE);
        edtSenha.setVisibility(View.GONE);
        btnEntrar.setVisibility(View.GONE);
    }

    private void InitComponentes() {

        loginButton = (LoginButton)findViewById(R.id.loginButton);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnEntrar = (Button)findViewById(R.id.btnEntrar);
        txtCadastro = (TextView)findViewById(R.id.txtCadastro);
    }


    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    loginError();
                }
            }
        });
    }


    private void loginError() {
        loginButton.setVisibility(View.VISIBLE);
        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();

    }

    private void naoLogou() {
        loginButton.setVisibility(View.VISIBLE);
        Toast.makeText(LoginActivity.this, "Não Logou", Toast.LENGTH_LONG).show();
    }

    private void logado() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }


}
