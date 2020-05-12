package com.development.community.ui.tasksUp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.community.EntryAdapter;
import com.development.community.R;
import com.development.community.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Fragment which displays tasks that the user is assigned to complete
 */
public class TasksUpFragment extends Fragment {
    private View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HomeFragment.OnPostButtonClickListener postButtonClickListener;
    HomeFragment.EntryAdapterMethods entryAdapterMethods;
    private RecyclerView recyclerView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("tasks");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EntryUpAdapter epa;
    LayoutUp lp;



    /**
     * Gets the adapter to load items into the RecyclerView
     */
    public interface EntryUpAdapter{
        EntryAdapter getUp();
    }

    /**
     * Gets the LayoutManager to load the RecyclerView
     */
    public interface LayoutUp{
        LinearLayoutManager getUpLayout();
    }

    /**
     * Run when the fragment is attached to the activity and assigns the Aapter and LayoutManager
     * @param context The context for the activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            epa = (EntryUpAdapter) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement something");
        }
        try{
            lp = (LayoutUp) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Dios");
        }
    }

    /**
     * Runs when the fragment is loaded, loads data into the recyclerview
     * @param inflater The inflater which loads the fragment into the activity
     * @param container The ViewGroup container for the View
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     * @return the View which should be loaded into the activity
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     
        View root = inflater.inflate(R.layout.fragment_tasks_up, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setLayoutManager(lp.getUpLayout());
                recyclerView.setAdapter(epa.getUp());
                String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }
}