package com.development.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class EntryAdapter extends BaseAdapter {
    private ArrayList<Entry> entryList;
    private Context context;
    EntryAdapter(Context context, ArrayList<Entry> entryList){
        super();
        this.entryList = entryList;
        this.context = context;

    }
    EntryAdapter(){
        super();
        this.entryList = new ArrayList<>();
    }
    
    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Object getItem(int i) {
        return entryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Entry entry = entryList.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.entry_layout, null);
        ((TextView) v.findViewById(R.id.tvTask)).setText(context.getString(R.string.task_string,
                entry.getTask()));
        ((TextView) v.findViewById(R.id.tvDate)).setText(entry.getDate().toString());
        ((TextView) v.findViewById(R.id.tvTime)).setText(entry.getTime().toString());
        ((TextView) v.findViewById(R.id.tvLocation)).setText(context.getString(R.string.location_string,
                entry.getDestination()));
        return v;
    }
}