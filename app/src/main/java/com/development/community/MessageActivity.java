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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import Notification.Client;
import Notification.Data;
import Notification.MyResponse;
import Notification.Sender;
import Notification.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The activity which allows users to send and view messages
 */
public class MessageActivity extends AppCompatActivity implements MessageAdapter.onMessageListener, UserAdapter.onUserListener{


        ImageButton sendButton;
        EditText sendText;
        Intent intent;
        MessageAdapter messageAdapter;
        List<Message> mchat;
        DatabaseReference reference;

        RecyclerView recyclerView;

        FirebaseUser fuser;
        DatabaseReference ref;
        FirebaseAuth firebaseAuth;

        static String path = "chats";

        String userid;

        APIService apiService;

        boolean notify = false;


    /**
     * Runs when the activity is started
     * Allows the user to type and send and view messages
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
    @Override
        public void onCreate(Bundle savedInstanceState) {

//        final TextView textView = root.findViewById(R.id.text_messaging);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_message);
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

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
                path = "";
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


         //Loads the user data from firebase
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


         // Sends the message when the sendButton is clicked
        sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notify = true;
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

    /**
     * Sends the message
     * @param send The UserID of the user who is sending the message
     * @param receive The UserID of the user to receive the mssage
     * @param message The message
     */
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

            final String msg = message;
            reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(notify)
                        sendNotification(receive,user.getName(),msg);
                    notify = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            Message message1 = new Message(send, sendName[0], receive, receiveName[0], message);

            ref.child(path).push().setValue(message1);
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        }

    /**
     * Sends a notification to the user that receives the message
     * @param receiver The UserID of the user that receives the message
     * @param username The username of the user that sends the message
     * @param message The message
     */
        private void sendNotification(String receiver, final String username, final String message){
            DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
            Query query = tokens.orderByKey().equalTo(receiver);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Token token = snapshot.getValue(Token.class);
                        Data data = new Data(fuser.getUid(),R.mipmap.ic_launcher,username + ": " + message, "New Message",userid);
                        Sender sender = new Sender(data,token.getToken());
                        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                if(response.code() == 200)
                                    if(response.body().success == 1)
                                        Toast.makeText(MessageActivity.this,"FAILED",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    /**
     * Loads messages that are sent and received into the recyclerview
     * @param myid the userID of the user
     * @param userid the UserID of the user that they are communicating with
     * @param imageurl The path to the profile picture of the user that they are communicating with
     */
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
