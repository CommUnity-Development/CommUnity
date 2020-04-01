package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;

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

        testArrayList.add(new Entry(new Date(5,12,2020), new Time(14,15),
                "Earth", "Buy groceries"));
        testArrayList.add(new Entry(new Date(10,11,2017), new Time(12,8),
                "Florida", "Watch a movie"));
        testArrayList.add(new Entry(new Date(6,23,1982), new Time(8,9),
                "Mars", "Gather rocks"));
        testArrayList.add(new Entry(new Date(5,12,2030), new Time(19,54),
                "California", "See the Golden Gate Bridge"));
        testArrayList.add(new Entry(new Date(5,12,1987), new Time(12,18),
                "Antarctica", "Hang out with penguins"));
        testArrayList.add(new Entry(new Date(5,12,2020), new Time(14,15),
                "Earth", "Buy groceries"));
        testArrayList.add(new Entry(new Date(10,11,2017), new Time(12,8),
                "Florida", "Watch a movie"));
        testArrayList.add(new Entry(new Date(6,23,1982), new Time(8,9),
                "Mars", "Gather rocks"));
        testArrayList.add(new Entry(new Date(5,12,2030), new Time(19,54),
                "California", "See the Golden Gate Bridge"));
        testArrayList.add(new Entry(new Date(5,12,1987), new Time(12,18),
                "Antarctica", "Hang out with penguins"));
        testArrayList.add(new Entry(new Date(5,12,2020), new Time(14,15),
                "Earth", "Buy groceries"));
        testArrayList.add(new Entry(new Date(10,11,2017), new Time(12,8),
                "Florida", "Watch a movie"));
        testArrayList.add(new Entry(new Date(6,23,1982), new Time(8,9),
                "Mars", "Gather rocks"));
        testArrayList.add(new Entry(new Date(5,12,2030), new Time(19,54),
                "California", "See the Golden Gate Bridge"));
        testArrayList.add(new Entry(new Date(5,12,1987), new Time(12,18),
                "Antarctica", "Hang out with penguins"));


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
//        Toast.makeText(this, "CLICKED", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra("Entry", testArrayList.get(position)); //Example of how to use it
        startActivity(intent);

    }
}
