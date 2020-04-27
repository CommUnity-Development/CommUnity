package com.development.community.ui.messaging;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.development.community.EntryAdapter;
import com.development.community.Message;
import com.development.community.R;
import com.development.community.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Message> mChat;
    private String imageurl;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Message> mChat, String imageurl) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }


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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Message chat = mChat.get(position);

        holder.show_message.setText((chat.getMessage()));

        if(imageurl.equals("default"))
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        else
            Glide.with(mContext).load(imageurl).into(holder.profile_image);



    }


    public int getItemCount(){
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        EntryAdapter.onEntryListener onEntryListener;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.showMessage);
            profile_image = itemView.findViewById(R.id.profilepic);
            itemView.setOnClickListener((View.OnClickListener) this);

        }

            public void onClick(View view){
                onEntryListener.onEntryClick(getAdapterPosition());

            }

    }

    public interface onMessageListener{
        void onMessageClick(int position);
    }


    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSenderUID().equals(fuser.getUid()))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;

    }



}
