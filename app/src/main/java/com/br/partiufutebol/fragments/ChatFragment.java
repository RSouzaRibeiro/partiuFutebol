package com.br.partiufutebol.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.partiufutebol.R;
import com.br.partiufutebol.adapters.ChatMessageAdapter;
import com.br.partiufutebol.model.ChatMessage;
import com.br.partiufutebol.test.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    /*EditText edtTexto;
    EditText edtLeitura;
    Button btnGravar;*/


    private Button sendBtn;
    private EditText messageTxt;
    private RecyclerView messagesList;
    private ChatMessageAdapter adapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);




        sendBtn = (Button) view.findViewById(R.id.sendBtn);
        messageTxt = (EditText) view.findViewById(R.id.messageTxt);
        messagesList = (RecyclerView) view.findViewById(R.id.messagesList);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("chat");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        messagesList.setHasFixedSize(false);
        messagesList.setLayoutManager(layoutManager);

        adapter = new ChatMessageAdapter(getActivity());
        messagesList.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                messagesList.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChatMessage chat = new ChatMessage(firebaseUser.getDisplayName(), messageTxt.getText().toString());
                // Push the chat message to the database
                databaseReference.push().setValue(chat);
                messageTxt.setText("");
            }
        });



databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
        adapter.addMessage(chat);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


        return view;
    }

}
