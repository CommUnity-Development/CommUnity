package com.development.community.ui.messaging;

import android.content.Intent;
import android.os.Bundle;
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

import com.development.community.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class MessagingFragment extends Fragment {

    ImageButton sendButton;
    EditText sendText;
    Intent intent;

    FirebaseUser fuser;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;

    String uid;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);
//        final TextView textView = root.findViewById(R.id.text_messaging);

        sendButton = root.findViewById(R.id.sendButton);
        sendText = root.findViewById(R.id.textSend);

        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("users").child(uid);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sendText.getText().toString();
                if(!msg.equals(""))
                    sendMessage(fuser.getUid(),uid,msg);
                else
                    Toast.makeText(getContext(),"Please Type Something to Send",Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }


    private void sendMessage(String send, String receive, String message){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("send",send);
        hashMap.put("receive",receive);
        hashMap.put("message",message);

        ref.child("chats").push().setValue(hashMap);
    }
}
