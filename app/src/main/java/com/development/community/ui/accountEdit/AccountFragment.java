package com.development.community.ui.accountEdit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.development.community.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {
    EditText userState,userTown,userAddress,userBio;
    Button saveButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);


        userState = root.findViewById(R.id.userState);
        userTown = root.findViewById(R.id.userTown);
        userAddress = root.findViewById(R.id.userAddress);
        userBio = root.findViewById(R.id.userBioEnter);

        saveButton = root.findViewById(R.id.saveButton);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");




        return root;


    }
}
