package com.development.community.ui.tasksUp;

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
import com.development.community.R;
import com.development.community.ui.home.HomeFragment;
import com.development.community.ui.home.HomeViewModel;
import com.development.community.ui.tasksUp.TasksUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TasksUpFragment extends Fragment {
    private View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HomeFragment.OnPostButtonClickListener postButtonClickListener;
    HomeFragment.EntryAdapterMethods entryAdapterMethods;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("tasks");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TasksUpFragment.EntryUpAdapter eua;
    TasksUpFragment.LayoutUp lu;


    private TasksUpViewModel tasksUpViewModel;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            postButtonClickListener = (HomeFragment.OnPostButtonClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnPostButtonClickListener");
        }
        try{
            entryAdapterMethods = (HomeFragment.EntryAdapterMethods) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EntryAdapterMethods");
        }
    }

    public interface EntryUpAdapter{
        EntryAdapter getUp();
    }

    public interface LayoutUp{
        LinearLayoutManager getUpLayout();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksUpViewModel =
                ViewModelProviders.of(this).get(TasksUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks_up, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setLayoutManager(lu.getUpLayout());
                recyclerView.setAdapter(eua.getUp());
                String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}
