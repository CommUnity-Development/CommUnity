package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.development.community.ui.home.HomeFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements EntryAdapter.onEntryListener,
        HomeFragment.OnPostButtonClickListener, HomeFragment.EntryAdapterMethods {
    EntryAdapter entryAdapter;
    ArrayList<Entry> entryArrayList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<Integer> statuses = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 123;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HomeFragment homeFragment = new HomeFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_home, homeFragment).commit();

//        MessagingFragment mf = new MessagingFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_home, mf).commit();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_tasksUp, R.id.nav_tasksPast, R.id.nav_messaging)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        final View navHeader = navigationView.getHeaderView(0);
        ImageView imgView = navHeader.findViewById(R.id.imageView);
//        imgView.setImageResource(R.drawable.nice);

        TextView username = navHeader.findViewById(R.id.name);
        if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName() != null)
        username.setText(firebaseAuth.getCurrentUser().getDisplayName());

        TextView email = navHeader.findViewById(R.id.email);
        if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail() != null)
            email.setText(firebaseAuth.getCurrentUser().getEmail());



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entryArrayList.clear();
                times.clear();
                dates.clear();
                tasks.clear();
                locations.clear();
                ids.clear();
                statuses.clear();
                usernames.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("DATASNAPSHOT", ds.toString());
                    Entry entry = ds.getValue(Entry.class);
                    entryArrayList.add(entry);
                    assert entry != null;
                    tasks.add(entry.getTask());
                    times.add(entry.getTime());
                    dates.add(entry.getDate());
                    locations.add(entry.getDestination());
                    ids.add(ds.getKey());
                    statuses.add(entry.getStatus());
                    usernames.add(entry.getClientUsername());

                }
                entryAdapter = new EntryAdapter(MainActivity.this, times, dates, locations, tasks, statuses, usernames, ids,
                        MainActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(MainActivity.this, "You are now signed in.",Toast.LENGTH_LONG).show();
                    TextView username = navHeader.findViewById(R.id.name);
                    if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName() != null)
                        username.setText(firebaseAuth.getCurrentUser().getDisplayName());

                    TextView email = navHeader.findViewById(R.id.email);
                    if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail() != null)
                        email.setText(firebaseAuth.getCurrentUser().getEmail());

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            entryArrayList.clear();
                            times.clear();
                            dates.clear();
                            tasks.clear();
                            locations.clear();
                            ids.clear();
                            statuses.clear();
                            usernames.clear();
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                Entry entry = ds.getValue(Entry.class);
                                entryArrayList.add(entry);
                                assert entry != null;
                                tasks.add(entry.getTask());
                                times.add(entry.getTime());
                                dates.add(entry.getDate());
                                locations.add(entry.getDestination());
                                ids.add(ds.getKey());
                                statuses.add(entry.getStatus());
                                usernames.add(entry.getClientUsername());

                            }
                            entryAdapter = new EntryAdapter(MainActivity.this, times, dates, locations, tasks, statuses, usernames, ids,
                                    MainActivity.this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
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
        intent.putExtra("ID", ids.get(position));
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onPostButtonClick() {
        startActivity(new Intent(MainActivity.this, EntryActivity.class));
    }

    @Override
    public EntryAdapter getAdapter() {
        return entryAdapter;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(MainActivity.this);
    }
}
