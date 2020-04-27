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
import com.development.community.ui.messaging.MessageAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUsers;
    private onUserListener mOnUserListener;

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
        onUserListener onUserListener;

        public ViewHolder(View itemView, onUserListener onUserListener){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            profilePic = itemView.findViewById(R.id.profilepic);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
            Log.d("CLICK", "ITEM CLICKED AT POSITION "+getAdapterPosition());
        }
    }


}
