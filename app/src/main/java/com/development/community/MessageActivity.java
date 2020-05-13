package com.development.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.development.community.ui.messaging.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity implements MessageAdapter.onMessageListener, UserAdapter.onUserListener{

        ImageButton sendButton;
        EditText sendText;
        Intent intent;
        MessageAdapter messageAdapter;
        List<Message> mchat;

        RecyclerView recyclerView;

        FirebaseUser fuser;
        DatabaseReference ref;
        FirebaseAuth firebaseAuth;

        static String path = "chats";

        String userid;



        @Override
        public void onCreate(Bundle savedInstanceState) {

//        final TextView textView = root.findViewById(R.id.text_messaging);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_message);

            Intent intent = getIntent();
            String idUser = intent.getStringExtra("IDuser");
            final String idReceiver = intent.getStringExtra("IDchosen");
//            Toast.makeText(this, idUser + idReceiver, Toast.LENGTH_SHORT).show();
            Log.e("idUserRec", idUser + idReceiver);
            path = "chats";
//            Log.i("idUser", idUser);
//            Log.i("chosen", idReceiver);

            try {
                if (idUser.compareTo(idReceiver) > 0) path = "chats/" + idUser + idReceiver;
                else path = "chats/" + idReceiver + idUser;
            }catch(Exception ignored) {
//                Toast.makeText(MessageActivity.this, ignored.getMessage(), Toast.LENGTH_LONG).show();
//                Toast.makeText(MessageActivity.this, ignored.getMessage(), Toast.LENGTH_LONG).show();
                path = "fdklsfkdlsfjsd";
            }
//            Toast.makeText()

            sendButton = findViewById(R.id.sendButton);
            sendText = findViewById(R.id.textSend);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageActivity.this);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);


            intent = Objects.requireNonNull(MessageActivity.this).getIntent();


            firebaseAuth = FirebaseAuth.getInstance();
            final String userid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            fuser = FirebaseAuth.getInstance().getCurrentUser();
            ref = FirebaseDatabase.getInstance().getReference("users").child(userid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    try {
                        readMessage(fuser.getUid(), idReceiver, user.getProfilePicUrl());
                    }catch(Exception ignored){
//                        Toast.makeText(MessageActivity.this, ignored.getMessage(), Toast.LENGTH_SHORT).show();
                        readMessage(fuser.getUid(), idReceiver, "https://cdnjs.loli.net/ajax/libs/material-design-icons/1.0.2/social/3x_ios/ic_person_black_48dp.png");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = sendText.getText().toString();
                    if(!msg.equals("")) {
                        sendMessage(fuser.getUid(), idReceiver, msg);
                        sendText.setText("");
                    }
                    else
                        Toast.makeText(MessageActivity.this,"Please Type Something to Send",Toast.LENGTH_SHORT).show();
                }
            });


        }


        private void sendMessage(final String send, final String receive, String message){

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final String[] sendName = {""};
            final String[] receiveName = {""};

            HashMap<String,Object> hashMap = new HashMap<>();


//            Log.d("idUser", idUser);
//            Log.d("idRec", idReceiver);


            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(path);
//            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String uid = ds.getKey();
                        if(uid.equals(send)) sendName[0] = ds.getValue(User.class).getName();
                        else if(uid.equals(receive)) receiveName[0] = ds.getValue(User.class).getName();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            Message message1 = new Message(send, sendName[0], receive, receiveName[0], message);

            ref.child(path).push().setValue(message1);
            Toast.makeText(this, "Added message to "+path, Toast.LENGTH_SHORT).show();
        }



        private void readMessage(final String myid, final String userid, final String imageurl){

            mchat = new ArrayList<>();

            ref = FirebaseDatabase.getInstance().getReference(path);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mchat.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Message chat = snapshot.getValue(Message.class);
                        assert chat != null;
                        if(chat.getReceiverUID().equals(myid) && chat.getSenderUID().equals(userid) || chat.getReceiverUID().equals(userid) && chat.getSenderUID().equals(myid))
                            mchat.add(chat);
                        messageAdapter = new MessageAdapter(MessageActivity.this,mchat,imageurl);
                        recyclerView.setAdapter(messageAdapter);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    @Override
    public void onMessageClick(int position) {

    }

    @Override
    public void onUserClick(int position) {

    }
}
