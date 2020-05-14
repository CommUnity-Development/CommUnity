package com.development.community.ui.messaging;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.development.community.R;
import com.development.community.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A class that helps load a message into the recycler view
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    FirebaseUser fuser;

    /**
     * a constructor with all data
     * @param mContext Context for the activity
     * @param mChat List of chats
     * @param imageurl the string of the URL for the profile picture of the other user
     */
    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;

    }

    /**
     * Creates a ViewHolder to load into the RecyclerView
     * @param parent The parent ViewGroup
     * @param viewType The type of View
     * @return A ViewHolder object
     */
    @Override @NonNull
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    /**
     * Loads data into the ViewHolder
     * @param holder the ViewHolder in which the data should be loaded
     * @param position The Position in the ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        Log.d("SHOW", holder.toString());

        try {
            holder.show_message.setText((chat.getMessage()));
            Log.d("SUCCESS", chat.getMessage());
        }catch(Exception e) {
            Log.d("ERROR", Objects.requireNonNull(e.getMessage()));
            Log.d("ERROR", holder.toString());
        }

        if(imageurl.equals("default"))
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        else
            Glide.with(mContext).load(imageurl).into(holder.profile_image);



    }

    /**
     * a getter that returns the number of chats
     * @return the size of the Chat List
     */
    public int getItemCount(){
        return mChat.size();
    }

    /**
     * An Inner Class for the ViewHolder which allows loading data into the RecyclerView and handling onClick listeners
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView show_message;
        public TextView left_message;
        public ImageView profile_image;
        UserAdapter.onUserListener onUserListener;

        public ViewHolder(View itemView) {
            super(itemView);


            show_message = itemView.findViewById(R.id.showMessage);

            profile_image = itemView.findViewById(R.id.profilepic);
            itemView.setOnClickListener((View.OnClickListener) this);
            onUserListener = (UserAdapter.onUserListener) mContext;

        }

        /**
         * Called when a Chat item is clicked
         * @param view The item which is clicked
         */
            @Override
            public void onClick(View view){
//                onUserListener.onUserClick(getAdapterPosition());

            }

    }
    /**
     * An interface which calls a function when a chat is clicked on
     */
    public interface onMessageListener{
        void onMessageClick(int position);
    }

    /**
     * tells whether or not the message should be displayed on the left or right,
     * where left is receiving and right is sending
     * @param position potision of the view type
     * @return the right or left
     */
    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSenderUID().equals(fuser.getUid()))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;

    }



}
