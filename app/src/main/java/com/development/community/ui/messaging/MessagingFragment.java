package com.development.community.ui.messaging;

import android.content.Intent;
import android.os.Bundle;
import com.development.community.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.community.Entry;
import com.development.community.R;
import com.development.community.User;
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

public class MessagingFragment extends Fragment {

    ImageButton sendButton;
    EditText sendText;
    Intent intent;
    MessageAdapter messageAdapter;
    List<Message> mchat;

    RecyclerView recyclerView;

    FirebaseUser fuser;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;

    String userid;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);
//        final TextView textView = root.findViewById(R.id.text_messaging);

        sendButton = root.findViewById(R.id.sendButton);
        sendText = root.findViewById(R.id.textSend);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        intent = Objects.requireNonNull(getActivity()).getIntent();


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
                    readMessage(fuser.getUid(), userid, user.getProfilePicUrl());
                }catch(Exception ignored){
//                    Toast.makeText(getContext(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
                    readMessage(fuser.getUid(), userid, "https://cdnjs.loli.net/ajax/libs/material-design-icons/1.0.2/social/3x_ios/ic_person_black_48dp.png");
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
                if(!msg.equals(""))
                    sendMessage(fuser.getUid(),userid,msg);
                else
                    Toast.makeText(getContext(),"Please Type Something to Send",Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }


    private void sendMessage(final String send, final String receive, String message){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final String[] sendName = {""};
        final String[] receiveName = {""};

        HashMap<String,Object> hashMap = new HashMap<>();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
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

        ref.child("chats").push().setValue(message1);
    }



    private void readMessage(final String myid, final String userid, final String imageurl){

        mchat = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message chat = snapshot.getValue(Message.class);
                    assert chat != null;
                    if(chat.getReceiverUID().equals(myid) && chat.getSenderUID().equals(userid) || chat.getReceiverUID().equals(userid) && chat.getSenderUID().equals(myid))
                        mchat.add(chat);
                    messageAdapter = new MessageAdapter(getContext(),mchat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
