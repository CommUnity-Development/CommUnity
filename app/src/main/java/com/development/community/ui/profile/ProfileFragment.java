package com.development.community.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.development.community.R;
import com.development.community.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView userState,userBio,userAddress,userTown;
    Button editButton;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    FirebaseAuth auth;



    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        userState = root.findViewById(R.id.userStateView);
        userBio = root.findViewById(R.id.userBioView);
        userAddress = root.findViewById(R.id.userAddressView);
        userTown = root.findViewById(R.id.userTownView);
        editButton = root.findViewById(R.id.editButton);




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uidReal = auth.getCurrentUser().getUid();
                DataSnapshot ds = dataSnapshot.child(uidReal);
                User user = ds.getValue(User.class);
                assert user != null;
                userState.setText(user.getState());
                userBio.setText(user.getBio());
                userAddress.setText(user.getAddress());
                userTown.setText(user.getTown());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

}
