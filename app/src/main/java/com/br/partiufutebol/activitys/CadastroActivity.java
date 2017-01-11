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
import com.br.partiufutebol.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    FirebaseUser usuario;
    private Activity activity = this;
    private TextView txtTeste;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtNome;
    private Button btnCadastro;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRefLer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initComponentes();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                 usuario = firebaseAuth.getCurrentUser();
                if(usuario != null){

                    //txtTeste.setText(usuario.getUid());

                }
            }
        };

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(CadastroActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                                } else {
                                    myRefLer = FirebaseDatabase.getInstance().getReference();

                                    Usuario user = new Usuario();
                                    user.setId(usuario.getUid());
                                    user.setNome(edtNome.getText().toString());
                                    user.setEmail(edtEmail.getText().toString());
                                    user.setSenha(edtSenha.getText().toString());

                                    myRefLer.child("Usuario").child(user.getId()).setValue(user);

                                    clearCompenentes();
                                    finish();
                                    goLoginActivity();
                                }
                            }
                        });
            }
        });

    }
    private void goLoginActivity() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void clearCompenentes() {

        edtNome.setText("");
        edtEmail.setText("");
        edtSenha.setText("");
    }


    private void initComponentes() {

        txtTeste = (TextView)findViewById(R.id.txtTeste);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnCadastro = (Button)findViewById(R.id.btnCadastro);
        edtNome = (EditText)findViewById(R.id.edtNome);
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
