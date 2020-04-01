package com.development.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EntryAdapter entryAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ListView x = null;
        ArrayList<Entry> testArrayList = new ArrayList<>();
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

        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Time> times = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();
        for(Entry item : testArrayList){
            tasks.add(item.getTask());
            times.add(item.getTime());
            dates.add(item.getDate());
            locations.add(item.getDestination());
        }
        entryAdapter = new EntryAdapter(MainActivity.this, times,dates,locations,tasks);
        recyclerView.setAdapter(entryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
