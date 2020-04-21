package com.development.community.ui.tasksPast;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.community.Entry;
import com.development.community.EntryAdapter;
import com.development.community.MainActivity;
import com.development.community.R;
import com.development.community.ui.home.HomeFragment;
import com.development.community.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TasksPastFragment extends Fragment {
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
    EntryPastAdapter epa;
    LayoutPast lp;


    private TasksPastViewModel tasksPastViewModel;

    public interface EntryPastAdapter{
        EntryAdapter getPast();
    }

    public interface LayoutPast{
        LinearLayoutManager getPastLayout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            epa = (EntryPastAdapter) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement something");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksPastViewModel =
                ViewModelProviders.of(this).get(TasksPastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks_past, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setLayoutManager(lp.getPastLayout());
                recyclerView.setAdapter(epa.getPast());
                String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }
}
