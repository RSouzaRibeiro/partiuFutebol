package com.br.partiufutebol.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.br.partiufutebol.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class testActivity extends AppCompatActivity {

    EditText edtTexto;
    EditText edtLeitura;
    Button btnGravar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference  databaseReference;

    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        edtTexto = (EditText)findViewById(R.id.edtTexto);
        edtLeitura = (EditText)findViewById(R.id.edtLeitura);
        btnGravar = (Button)findViewById(R.id.btnGravar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("chat");




        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mensagem = edtTexto.getText().toString();

                Usuario user = new Usuario();
                user.setUsuario("Rafael");
                user.setMensagem(mensagem);

                databaseReference.child("mensagens").setValue(user);

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Usuario mensagem = dataSnapshot.child("mensagens").getValue(Usuario.class);
                if(mensagem!=null){
                    edtLeitura.setText(mensagem.getUsuario().toString().toUpperCase()+"\n"+mensagem.getMensagem().toString()+"\n\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
