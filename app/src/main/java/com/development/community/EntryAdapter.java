package com.development.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The adapter that allows Entries to be loaded in a RecyclerView
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> implements Serializable {
    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<Time> times;
    private ArrayList<Date> dates;
    private ArrayList<CommUnityLocation> locations;
    private ArrayList<String> tasks;
    private ArrayList<String> usernames;
    private ArrayList<Integer> statuses;
    private static Context context;
    private onEntryListener mOnEntryListener;
    private ArrayList<String> ids;



    /**
     * Constructor for an EntryAdapter
      * @param context The context for the activity
     * @param times A list of the times for the entries
     * @param dates A list of the dates for the entries
     * @param locations A list of the locations for the entries
     * @param tasks A list of the task descriptions for the entries
     * @param statuses A list of the statuses for the entries
     * @param usernames A list of the client usernames for the entries
     * @param ids A list of the ids for the entries
     * @param mOnEntryListener An OnEntryListener which allows a function to be called when the entry is clicked.
     */
    EntryAdapter(Context context, ArrayList<Time> times, ArrayList<Date> dates, ArrayList<CommUnityLocation> locations, ArrayList<String> tasks, ArrayList<Integer> statuses, ArrayList<String> usernames, ArrayList<String> ids, onEntryListener mOnEntryListener) {
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

    /**
     * Creates a ViewHolder which can be used with onBindViewHolder to load data into the RecyclerView
     * @param parent The parent ViewGroup
     * @param viewType The type of view used
     * @return a ViewHolder which can be used with onBindViewHolder to load data into the RecyclerView
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_layout, parent, false);
        return new ViewHolder(view, mOnEntryListener);


    }

    /**
     * Loads the data into the RecyclerView
     * @param holder The ViewHolder
     * @param position The position in the RecyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.tvTask.setText(tasks.get(position));
        holder.tvDate.setText(dates.get(position).toString());
        holder.tvTime.setText(times.get(position).toString());
        Log.d("Position", String.valueOf(position));
        Log.d("Position", tasks.get(position));
        holder.tvLocation.setText(locations.get(position).getProvider());
        holder.tvUsername.setText("Requested by " +usernames.get(position));
        holder.tvStatus.setText("Status: " + Globals.getStatuses()[statuses.get(position)]);
        switch(statuses.get(position)){
            case 0:
                holder.statusImageView.setImageResource(android.R.drawable.presence_busy);
                break;
            case 1:
                holder.statusImageView.setImageResource(android.R.drawable.presence_away);
                break;
            default:
                holder.statusImageView.setImageResource(android.R.drawable.presence_online);
                 break;
        }
    }

    /**
     * Returns the number of items in the RecyclerView
     * @return the number of items in the RecyclerView
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * An interface which allows a function to be run when an entry is clicked
     */
    public interface onEntryListener{
        void onEntryClick(int position);
    }

    /**
     * An inner ViewHolder class which allows data to be loaded into the RecyclerView
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDate;
        TextView tvTask;
        TextView tvTime;
        TextView tvLocation;
        LinearLayout layout;
        TextView tvStatus;
        TextView tvUsername;
        onEntryListener onEntryListener;
        ImageView statusImageView;

        /**
         * The constructor for the ViewHolder
         * @param itemView The view to be loaded into the RecyclerView
         * @param onEntryListener The interface to be implemented to allow a function to be run when the entry is clicked
         */
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
            statusImageView = itemView.findViewById(R.id.taskStatus);

            itemView.setOnClickListener(this);

            statusImageView.setVisibility(View.VISIBLE);
        }

        /**
         * The function to be run when an entry is clicked
         * @param view the view that is clicked
         */
        @Override
        public void onClick(View view) {
            onEntryListener.onEntryClick(getAdapterPosition());
            Log.d(TAG, "onClick: success");

        }
    }
}