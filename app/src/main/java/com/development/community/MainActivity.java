package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.List;
import java.util.Objects;

import com.development.community.ui.home.HomeFragment;
import com.development.community.ui.messaging.MessageAdapter;
import com.development.community.ui.profile.ProfileFragment;
import com.development.community.ui.tasksPast.TasksPastFragment;
import com.development.community.ui.tasksUp.TasksUpFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * The Main Activity which loads after the splash screen.
 * This activity links to 5 fragments (home, profile, upcoming tasks, past tasks, and messaging)
 * It also contains the onClick methods for recyclerViews and implements interfaces from the fragments
 */
public class MainActivity extends AppCompatActivity implements EntryAdapter.onEntryListener,
        HomeFragment.OnPostButtonClickListener, HomeFragment.EntryAdapterMethods,
        ProfileFragment.profileFunc,TasksPastFragment.EntryPastAdapter,TasksPastFragment.LayoutPast,
        TasksUpFragment.EntryUpAdapter,TasksUpFragment.LayoutUp, UserAdapter.onUserListener {
    EntryAdapter entryAdapter;
    EntryAdapter entryAdapterPast;
    EntryAdapter entryAdapterUp;
    ArrayList<Entry> entryArrayList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencePast;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<CommUnityLocation> locations = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<Integer> statuses = new ArrayList<>();
    FirebaseAuth firebaseAuth;

    ArrayList<Entry> entryArrayListPast = new ArrayList<>();
    ArrayList<String> tasksPast = new ArrayList<>();
    ArrayList<Time> timesPast = new ArrayList<>();
    ArrayList<Date> datesPast = new ArrayList<>();
    ArrayList<CommUnityLocation> locationsPast = new ArrayList<>();
    ArrayList<String> idsPast = new ArrayList<>();
    ArrayList<String> usernamesPast = new ArrayList<>();
    ArrayList<Integer> statusesPast = new ArrayList<>();

    ArrayList<Entry> entryArrayListUp = new ArrayList<>();
    ArrayList<String> tasksUp = new ArrayList<>();
    ArrayList<Time> timesUp = new ArrayList<>();
    ArrayList<Date> datesUp = new ArrayList<>();
    ArrayList<CommUnityLocation> locationsUp = new ArrayList<>();
    ArrayList<String> idsUp = new ArrayList<>();
    ArrayList<String> usernamesUp = new ArrayList<>();
    ArrayList<Integer> statusesUp = new ArrayList<>();

    ArrayList<String> userIDS = new ArrayList<>();


    FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 123;

    private AppBarConfiguration mAppBarConfiguration;


    /**
     * Called once the activity loads
     * Loads firebase data, sets fragment managers, and creates the navigation drawer
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("updates");


        // Loads Firebase Data for tasks
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");
        databaseReferencePast = firebaseDatabase.getReference("tasks");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference userDatabase = firebaseDatabase.getReference("Users");

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    userIDS.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Creates and initializes the navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Source: https://stackoverflow.com/questions/57702646/how-to-fix-android-studio-3-5-navigation-activity-template-onnavigationitemselec
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_tasksUp, R.id.nav_tasksPast, R.id.nav_messaging)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        final View navHeader = navigationView.getHeaderView(0);
        ImageView imgView = navHeader.findViewById(R.id.imageView);

        TextView username = navHeader.findViewById(R.id.name);
        try {
            if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName() != null)
                username.setText(firebaseAuth.getCurrentUser().getDisplayName());
        }catch(NullPointerException e){
            Log.d("TAG", "Null Username");
        }
        try {
            TextView email = navHeader.findViewById(R.id.email);
            if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail() != null)
                email.setText(firebaseAuth.getCurrentUser().getEmail());
        }catch(NullPointerException e){
            Log.d("TAG", "Null Email");
        }
        try{
            //TODO: Set this up
            ImageView pfp = navHeader.findViewById(R.id.imageView);
            try {
                String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

            }catch(Exception e){
                Log.d("TAG", "Null User");
            }

        }catch(NullPointerException e){
            Log.d("TAG", "Null PFP");
        }

        // Loads data into RecyclerView for entries
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

        tasksPastFilter();
        tasksUpFilter();

        // Checks if user is signed in and refreshes page once user signs in
        authStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    if(!Globals.getRestarted()){
                        recreate();
                        Globals.setRestarted(true);
                    }

                    onSignedInInitialize(user.getDisplayName());
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

    /**
     * Checks which NavigationView item is selected
      * @param navigationView The navigation view
     * @return the index of the selected item
     */
    private int getCheckedItem(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Runs when the user clicks on an entry
     * Loads the SignUpActivity and passes the id as an extra
     * @param position the position of the entry in the RecyclerView
     */
    @Override
    public void onEntryClick(int position) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(getCheckedItem(navigationView) == 0) {
            if (entryArrayList.get(position).getServerUID() == null ||
                    entryArrayList.get(position).getServerUID().equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())) {
//            Log.d("UID", entryArrayList.get(position).getServerUID());
                Intent intent = new Intent(this, SignUpActivity.class);
                intent.putExtra("ID", ids.get(position));
                Log.d("Fail", "A");
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Someone else has already signed up for this task", Toast.LENGTH_SHORT).show();
                Log.d("FAIL", "B");
            }
        }
//        else if(getVisibleFragment()==null) {
//            Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
//            Log.d("FAIL", "C");
//        }
//        else{
//            Log.d("FAIL", getVisibleFragment().toString());
//        }


    }




    /**
     * Runs when an item in the menu is selected
     * @param item the selected item
     * @return true if the selection is successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Runs when the application is paused
     * Removes the current AuthStateListener
     */
    @Override
    protected void onPause(){
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);

    }

    /**
     * Runs when the application is resumed
     * Adds the AuthStateListener back
     */
    @Override
    protected void onResume(){
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    /**
     * Runs when a user signs in
     * @param username The user's username
     */
    private void onSignedInInitialize(String username){
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
    }


    /**
     * Run when the options menu is created
     * Loads items into the menu
     * @param menu the menu which contains the items
     * @return always returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Runs when the user navigates up
     * Changes the fragment when the user uses the navigation drawer
     * @return NavigateUp boolean value
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Run when the PostButton is clicked in the HomeFragment
     * Loads the EntryActivity
     */
    @Override
    public void onPostButtonClick() {
        startActivity(new Intent(MainActivity.this, EntryActivity.class));
    }

    /**
     * Returns the EntryAdapter (passes it to the HomeFragment)
     * @return the EntryAdapter of all tasks
     */
    @Override
    public EntryAdapter getAdapter() {
        return entryAdapter;
    }

    /**
     * Returns completed tasks (passes it to TasksPastFragment)
     * @return the EntryAdapter of completed tasks
     */
    public EntryAdapter getPast(){
        return entryAdapterPast;
    }

    /**
     * Returns upcoming tasks (passes it to TasksUpFragment)
     * @return the EntryAdapter of upcoming tasks
     */
    public EntryAdapter getUp(){
        return entryAdapterUp;
    }

    /**
     * Creates and returns a new LinearLayoutManager (for the HomeFragment's RecyclerView)
     * @return a LinearLayoutManager
     */
    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(MainActivity.this);
    }

    /**
     * Creates and returns a new LinearLayoutManager (for the TasksPastFragment's RecyclerView)
     * @return a LinearLayoutManager
     */
    @Override
    public LinearLayoutManager getPastLayout() {
        return new LinearLayoutManager(MainActivity.this);
    }

    /**
     * Creates and returns a new LinearLayoutManager (for the TasksUpFragment's RecyclerView)
     * @return a LinearLayoutManager
     */
    public LinearLayoutManager getUpLayout() {
        return new LinearLayoutManager(MainActivity.this);
    }

    /**
     * Runs when the user clicks the "edit" button when viewing their profile
     * Passes data as Extras and allows the user to edit their profile
     * @param name The user's name
     * @param state The user's state
     * @param town The user's town
     * @param address The user's address
     * @param bio The user's bio
     */
    @Override
    public void edit(String name, String state, String town, String address, String bio) {
        Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("state", state);
        intent.putExtra("town", town);
        intent.putExtra("address", address);
        intent.putExtra("bio", bio);
        startActivity(intent);
    }

    /**
     * Filters out only completed (past) tasks and sets EntryAdapterPast to only contain these tasks
     */
    private void tasksPastFilter(){
        databaseReferencePast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                entryArrayListPast.clear();
                timesPast.clear();
                datesPast.clear();
                tasksPast.clear();
                locationsPast.clear();
                idsPast.clear();
                statusesPast.clear();
                usernamesPast.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("DATASNAPSHOT", ds.toString());
                    Entry entry = ds.getValue(Entry.class);
                    assert entry != null;
                    if(uid.equals(entry.getServerUID()) && entry.getStatus() == 2) {
                        entryArrayListPast.add(entry);
                        tasksPast.add(entry.getTask());
                        timesPast.add(entry.getTime());
                        datesPast.add(entry.getDate());
                        locationsPast.add(entry.getDestination());
                        idsPast.add(ds.getKey());
                        statusesPast.add(entry.getStatus());
                        usernamesPast.add(entry.getClientUsername());
                    }
                }
                entryAdapterPast = new EntryAdapter(MainActivity.this, timesPast, datesPast, locationsPast, tasksPast, statusesPast, usernamesPast, idsPast,
                        MainActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * Filters out only upcoming tasks and sets EntryAdapterPast to only contain these tasks
     */
    private void tasksUpFilter(){
        databaseReferencePast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                entryArrayListUp.clear();
                timesUp.clear();
                datesUp.clear();
                tasksUp.clear();
                locationsUp.clear();
                idsUp.clear();
                statusesUp.clear();
                usernamesUp.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("DATASNAPSHOT", ds.toString());
                    Entry entry = ds.getValue(Entry.class);
                    assert entry != null;
                    if(uid.equals(entry.getServerUID()) && entry.getStatus() == 1) {
                        entryArrayListUp.add(entry);
                        tasksUp.add(entry.getTask());
                        timesUp.add(entry.getTime());
                        datesUp.add(entry.getDate());
                        locationsUp.add(entry.getDestination());
                        idsUp.add(ds.getKey());
                        statusesUp.add(entry.getStatus());
                        usernamesUp.add(entry.getClientUsername());
                    }
                }
                entryAdapterUp = new EntryAdapter(MainActivity.this, timesUp, datesUp, locationsUp, tasksUp, statusesUp, usernamesUp, idsUp,
                        MainActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    /**
     * Runs when a User is clicked
     * Opens a messaging area where the user can chat with the user that they selected
     * @param user The selected user
     */
    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(this, MessageActivity.class);
        Log.v("IDS", userIDS.toString());
        intent.putExtra("IDchosen", user.getUid());
//        Toast.makeText(MainActivity.this, "ID Chosen: "+user.getUid()+", Your ID: "+firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
        intent.putExtra("IDuser", firebaseAuth.getCurrentUser().getUid());
        startActivity(intent);

    }
}
