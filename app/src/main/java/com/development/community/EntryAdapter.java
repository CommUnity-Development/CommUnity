package com.development.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder>{
    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<Time> times;
    private ArrayList<Date> dates;
    private ArrayList<String> locations;
    private ArrayList<String> tasks;
    private ArrayList<String> usernames;
    private ArrayList<Integer> statuses;
    private static Context context;
    private onEntryListener mOnEntryListener;
    private ArrayList<String> ids;

    EntryAdapter(Context context, ArrayList<Time> times, ArrayList<Date> dates, ArrayList<String> locations, ArrayList<String> tasks, ArrayList<Integer> statuses, ArrayList<String> usernames, ArrayList<String> ids, onEntryListener mOnEntryListener) {
        this.times = times;
        this.dates = dates;
        this.locations = locations;
        this.tasks = tasks;
        this.context = context;
        this.mOnEntryListener = mOnEntryListener;
        this.statuses = statuses;
        this.usernames = usernames;
        this.ids = ids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_layout, parent, false);
        return new ViewHolder(view, mOnEntryListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.tvTask.setText(tasks.get(position));
        holder.tvDate.setText(dates.get(position).toString());
        holder.tvTime.setText(times.get(position).toString());
        holder.tvLocation.setText(locations.get(position));
        holder.tvUsername.setText("Requested by " +usernames.get(position));
        holder.tvStatus.setText("Status: " +Controller.statuses[statuses.get(position)]);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public interface onEntryListener{
        void onEntryClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDate;
        TextView tvTask;
        TextView tvTime;
        TextView tvLocation;
        LinearLayout layout;
        TextView tvStatus;
        TextView tvUsername;
        onEntryListener onEntryListener;

        ViewHolder(@NonNull View itemView, onEntryListener onEntryListener) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTask = itemView.findViewById(R.id.tvTask);
            layout = itemView.findViewById(R.id.linearLayout);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            this.onEntryListener = onEntryListener;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onEntryListener.onEntryClick(getAdapterPosition());
            Log.d(TAG, "onClick: success");

        }
    }
}