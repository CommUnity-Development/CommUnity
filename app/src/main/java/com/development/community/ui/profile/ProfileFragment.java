package com.development.community.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.development.community.R;
import com.development.community.User;
import com.development.community.ui.accountEdit.AccountFragment;
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
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public interface profileFunc{
        void edit();
    }


    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userState = root.findViewById(R.id.userStateView);
        userBio = root.findViewById(R.id.userBioView);
        userAddress = root.findViewById(R.id.userAddressView);
        userTown = root.findViewById(R.id.userTownView);
        editButton = root.findViewById(R.id.saveButton);




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uidReal = auth.getCurrentUser().getUid();
                DataSnapshot ds = dataSnapshot.child(uidReal);
                User user = ds.getValue(User.class);
                if(user != null) {
                    userState.setText(user.getState());
                    userBio.setText(user.getBio());
                    userAddress.setText(user.getAddress());
                    userTown.setText(user.getTown());
                }
                else{
                    userState.setText("Null");
                    userBio.setText("Null");
                    userAddress.setText("Null");
                    userTown.setText("Null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profileFunc profileFunc = (ProfileFragment.profileFunc) getContext();
            }

        });

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    profileFunc pf = (profileFunc) getContext();
                    assert pf != null;
                    pf.edit();
                }catch(Exception e){
                    Log.d("TAG", "Fail"+e.getMessage());

                }
            }
        });

        return root;
    }

}
