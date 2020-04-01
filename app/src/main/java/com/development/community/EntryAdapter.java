package com.development.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder>{
    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<Time> times = new ArrayList<>();
    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> tasks = new ArrayList<>();
    private Context context;

    public EntryAdapter( Context context,  ArrayList<Time> times, ArrayList<Date> dates, ArrayList<String> locations, ArrayList<String> tasks) {
        this.times = times;
        this.dates = dates;
        this.locations = locations;
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.tvTask.setText(tasks.get(position));
        holder.tvDate.setText(dates.get(position).toString());
        holder.tvTime.setText(times.get(position).toString());
        holder.tvLocation.setText(locations.get(position));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "An item was clicked", Toast.LENGTH_SHORT).show();
                // Replace with allowing the user to sign up to complete the task
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvTask;
        TextView tvTime;
        TextView tvLocation;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTask = itemView.findViewById(R.id.tvTask);
            layout = itemView.findViewById(R.id.linearLayout);
        }
    }
}