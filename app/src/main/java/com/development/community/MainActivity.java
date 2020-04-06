package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 123;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("test");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        ImageButton postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EntryActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


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

        authStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(MainActivity.this, "You are now signed in.",Toast.LENGTH_LONG).show();
                }else{
                    onSignedOutCleanUp();
                    //user is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    )).build(), RC_SIGN_IN
                    );
                }
            }
        };
    }


    @Override
    public void onEntryClick(int position) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("Entry", testArrayList.get(position)); //Example of how to use it
        startActivity(intent);

    }

    @Override
    protected void onPause(){
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void onSignedInInitialize(String username){

    }

    private void onSignedOutCleanUp(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
