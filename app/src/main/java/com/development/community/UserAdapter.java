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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUsers;
    String theLastMessage;
    onUserListener mOnUserListener;

    public UserAdapter(Context givenContext, List<User> givenUsers, onUserListener mOnUserListener){
        mUsers = givenUsers;
        mContext = givenContext;
        this.mOnUserListener = mOnUserListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("ONBIND", "VIEW HOLDER HAS BEEN BINDED");
        User user = mUsers.get(position);
        holder.username.setText(user.getName());
        if(user.getProfilePicUrl().equals(""))
            holder.profilePic.setImageResource(R.drawable.ic_person_black_24dp);
        else
            Glide.with(mContext).load(user.getProfilePicUrl()).into(holder.profilePic);

    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public interface onUserListener{
        void onUserClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView username;
        public CircleImageView profilePic;
        private TextView lastMsg;
        onUserListener onUserListener;

        public ViewHolder(View itemView, onUserListener onUserListener){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            profilePic = itemView.findViewById(R.id.profilepic);
            lastMsg = itemView.findViewById(R.id.lastMsg);

        }

    }

    private void lastMessage(final String userid, final TextView lastMsg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiverUID().equals(firebaseUser.getUid()) && chat.getSenderUID().equals(userid) || chat.getReceiverUID().equals(userid) && chat.getSenderUID().equals(firebaseUser.getUid()))
                        theLastMessage = chat.getMessage();
                }
                switch (theLastMessage){
                    case "default":
                        lastMsg.setText("No Message");
                        break;

                    default:
                        lastMsg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);

            }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
            Log.d("CLICK", "ITEM CLICKED AT POSITION "+getAdapterPosition());
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
