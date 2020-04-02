package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements EntryAdapter.onEntryListener{
    EntryAdapter entryAdapter;
    ArrayList<Entry> testArrayList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("test");

        ImageButton postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EntryActivity.class));
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Entry entry = ds.getValue(Entry.class);
                    testArrayList.add(entry);
//                    assert entry != null;
                    assert entry != null;
                    tasks.add(entry.getTask());
                    times.add(entry.getTime());
                    dates.add(entry.getDate());
                    locations.add(entry.getDestination());
                }
                entryAdapter = new EntryAdapter(MainActivity.this, times, dates, locations, tasks,
                        MainActivity.this);
                recyclerView.setAdapter(entryAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onCancelled: ");
            }
        });

        for(Entry item : testArrayList){
            tasks.add(item.getTask());
            times.add(item.getTime());
            dates.add(item.getDate());
            locations.add(item.getDestination());
        }
        entryAdapter = new EntryAdapter(MainActivity.this, times, dates, locations, tasks, this);
        recyclerView.setAdapter(entryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onEntryClick(int position) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("Entry", testArrayList.get(position)); //Example of how to use it
        startActivity(intent);

    }
}
