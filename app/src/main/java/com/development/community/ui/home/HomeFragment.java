package com.development.community.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.community.EntryAdapter;
import com.development.community.MainActivity;
import com.development.community.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The fragment which loads the recyclerview (the default fragment for the activity)
 */
public class HomeFragment extends Fragment{
    private View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    OnPostButtonClickListener postButtonClickListener;
    EntryAdapterMethods entryAdapterMethods;
    RecyclerView recyclerView;

    /**
     * Default Constructor
     */
    public HomeFragment(){

    }

    /**
     * Sets the RecyclerView's Adapter and LayoutManager
     */
    public void setAdapterAndLayout(){
        recyclerView.setLayoutManager(entryAdapterMethods.getLayoutManager());
        recyclerView.setAdapter(entryAdapterMethods.getAdapter());
    }


    /**
     * Used to set the onClick method for entries
     */
    public interface OnPostButtonClickListener{
        void onPostButtonClick();
    }

    /**
     * Used to get the EntryAdapter and LinearLayoutManager
     */
    public interface EntryAdapterMethods{
        EntryAdapter getAdapter();
        LinearLayoutManager getLayoutManager();

    }

    /**
     * Called when the fragment is attached to the activity
     * @param context the context for the activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            postButtonClickListener = (OnPostButtonClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnPostButtonClickListener");
        }
        try{
            entryAdapterMethods = (EntryAdapterMethods) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EntryAdapterMethods");
        }
    }

    /**
     * Runs when the fragment is loaded
     * @param inflater The inflater which loads the fragment into the activity
     * @param container The ViewGroup container for the View
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     * @return the View which should be loaded into the activity
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_home, container, false);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        ImageButton postButton = root.findViewById(R.id.postButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setAdapterAndLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Runs when the "Post" button is clicked
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postButtonClickListener.onPostButtonClick();
            }
        });


        return root;
    }

// recyclerView.setAdapter(entryAdapter);
// recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



}
