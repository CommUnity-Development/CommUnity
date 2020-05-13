package com.development.community.ui.messaging;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.development.community.MainActivity;
import com.development.community.R;
import com.development.community.User;
import com.development.community.UserAdapter;
import com.development.community.ui.messaging.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import Notification.Token;


/**
 * Fragment that shows all of the users to chat with
 */
public class ChatManager extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private HashSet<User> mUsers;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> userList;
    String uidUserFinal;

    /**
     * Runs when the fragment is loaded, loads data into the recyclerview
     * @param inflater The inflater which loads the fragment into the activity
     * @param container The ViewGroup container for the View
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     * @return the View which should be loaded into the activity
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_manager, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        uidUserFinal = fuser.getUid();



        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    Log.i("MESSAGE", chat.getReceiverUID() + " , " + chat.getSenderUID());
                    if(chat.getSenderUID().equals(fuser.getUid()))
                        userList.add(chat.getReceiverUID());

                    if(chat.getReceiverUID().equals(fuser.getUid()))
                        userList.add(chat.getSenderUID());

                }
                UserAdapter userAdapter = readChat();

//                Log.i("Stuff", String.valueOf(userAdapter.getItemCount()));
                Log.i("ADAPTER", "ADAPTER \"Set\"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }


    /**
     * This updates the token of each user and stores it in Firebase
     * @param token The token desired to be stored
     */
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);

        reference.child(fuser.getUid()).setValue(token1);
    }

    /**
     * reads the chat from the user and makes a list of users to chat with
     * @return a user adapter with the possible users to chat with
     */
    private UserAdapter readChat(){
        mUsers = new HashSet<>();
        final UserAdapter[] userAdapter = new UserAdapter[1];


        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    for(String id : userList) {
                        assert user != null;
                        if (user.getUid().equals(id)) {
                            mUsers.add(user);
                        }
                    }
                }

                Log.i("Musers", mUsers.toString());


                ArrayList<User> mUserAL = new ArrayList<>(mUsers);


                userAdapter[0] = new UserAdapter(getContext(),mUserAL, (UserAdapter.onUserListener) getActivity());
                Log.i("LENGTH", String.valueOf(userAdapter[0].getItemCount()));
                recyclerView.setAdapter(userAdapter[0]);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                Log.i("UserID",uidUserFinal);


            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(!user.getUid().equals(uidUserFinal))
                        mUsers.add(user);
                }

//                mUsers.remove()

                ArrayList<User> mUserAL = new ArrayList<>(mUsers);
                userAdapter[0] = new UserAdapter(getContext(),mUserAL, (UserAdapter.onUserListener) getActivity());
                Log.i("LENGTH", String.valueOf(userAdapter[0].getItemCount()));
                recyclerView.setAdapter(userAdapter[0]);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return userAdapter[0];


    }

}
