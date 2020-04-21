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

public class HomeFragment extends Fragment{
    private View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    OnPostButtonClickListener postButtonClickListener;
    EntryAdapterMethods entryAdapterMethods;
    RecyclerView recyclerView;




    public HomeFragment(){

    }

    public void setAdapterAndLayout(){
        recyclerView.setLayoutManager(entryAdapterMethods.getLayoutManager());
        recyclerView.setAdapter(entryAdapterMethods.getAdapter());
    }



    public interface OnPostButtonClickListener{
        void onPostButtonClick();
    }

    public interface EntryAdapterMethods{
        EntryAdapter getAdapter();
        LinearLayoutManager getLayoutManager();

    }

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

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
