package com.development.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.development.community.*;
import com.development.community.ui.messaging.Chat;
import com.development.community.ui.messaging.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * UserAdapter class used to load users into a RecyclerView
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUsers;
    String theLastMessage;
    onUserListener mOnUserListener;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    /**
     * Constructor
     * @param givenContext Context for the activity
     * @param givenUsers A list of Users
     * @param mOnUserListener A Listener for when a user is clicked
     */
    public UserAdapter(Context givenContext, List<User> givenUsers, onUserListener mOnUserListener){
        mUsers = givenUsers;
        mContext = givenContext;
        this.mOnUserListener = mOnUserListener;
    }

    /**
     * Creates a ViewHolder to load into the RecyclerView
     * @param parent The parent ViewGroup
     * @param viewType The type of View
     * @return A ViewHolder object
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view, mOnUserListener, mUsers);
    }

    /**
     * Loads data into the ViewHolder
     * @param holder the ViewHolder in which the data should be loaded
     * @param position The Position in the ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("ONBIND", "VIEW HOLDER HAS BEEN BINDED");
        User user = mUsers.get(position);
        holder.username.setText(user.getName());
        if(user.getProfilePicUrl().equals(""))
            holder.profilePic.setImageResource(R.drawable.ic_person_black_24dp);
        else
            Glide.with(mContext).load(user.getProfilePicUrl()).into(holder.profilePic);

        lastMessage(user.getUid(), holder.lastMsg);

    }

    /**
     * Returns the number of Users
     * @return the number of Users
     */
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * An interface which calls a function when a User is clicked
     */
    public interface onUserListener{
        void onUserClick(User user);
    }

    /**
     * An Inner Class for the ViewHolder which allows loading data into the RecyclerView and handling onClick listeners
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView username;
        public CircleImageView profilePic;
        private TextView lastMsg;
        private List<User> userList;
        onUserListener onUserListener;

        /**
         * Constructor
         * @param itemView the View to load in the ViewHolder
         * @param onUserListener The listener for onClick functions
         */
        public ViewHolder(View itemView, onUserListener onUserListener, List<User> userList){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            profilePic = itemView.findViewById(R.id.profilepic);
            lastMsg = itemView.findViewById(R.id.lastMsg);
            this.onUserListener = onUserListener;
            this.userList = userList;
            itemView.setOnClickListener(this);



        }

        /**
         * Called when a User item is clicked
         * @param view The item which is clicked
         */
        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(userList.get(getAdapterPosition()));
            Log.d("CLICK", "ITEM CLICKED AT POSITION "+getAdapterPosition());
        }
    }

    /**
     * Loads the last message sent into the View
     * @param userid The ID of the user
     * @param lastMsg The last message sent
     */
    public void lastMessage(final String userid, final TextView lastMsg) {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    assert chat != null;
                    assert firebaseUser != null;
                    Log.i("CHATMSG:",chat.getMessage());
                    if (chat.getReceiverUID().equals(firebaseUser.getUid()) && chat.getSenderUID().equals(userid) || chat.getSenderUID().equals(firebaseUser.getUid()) && chat.getReceiverUID().equals(userid))
                        theLastMessage = chat.getMessage();
                }
//                switch (theLastMessage) {
//                    case "default":
//                        lastMsg.setText("No Message");
//                        break;
//
//                    default:
//                        lastMsg.setText(theLastMessage);
//                        break;
//                }

                lastMsg.setText(theLastMessage);
                theLastMessage = "default";

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
