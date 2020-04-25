package com.development.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.development.community.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context givenContext, List<User> givenUsers){
        mUsers = givenUsers;
        mContext = givenContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.)
//        return new UserAdapter();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUsers.get(position);
        holder.username.setText(user.getName());
        if(user.getProfilePicUrl().equals("default"))
            holder.profilePic.setImageResource(R.drawable.ic_person_black_24dp);
        else
            Glide.with(mContext).load(user.getProfilePicUrl()).into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profilePic;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            profilePic = itemView.findViewById(R.id.profilepic);

        }

    }


}
