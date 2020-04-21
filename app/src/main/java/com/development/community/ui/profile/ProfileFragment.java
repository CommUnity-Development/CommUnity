package com.development.community.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.development.community.R;
import com.development.community.User;
import com.development.community.ui.accountEdit.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


//TODO: Retrieve the profile picture from Firebase
public class ProfileFragment extends Fragment {
    TextView userState, userBio, userAddress, userTown,userName;
    Button editButton;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid;
    final String TAG = "STORAGE";

    private ImageView userPic;

    public interface profileFunc{
        void edit();
    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userPic = root.findViewById(R.id.userProfPic);
        userName = root.findViewById(R.id.userName);
        userState = root.findViewById(R.id.userStateView);
        userBio = root.findViewById(R.id.userBioView);
        userAddress = root.findViewById(R.id.userAddressView);
        userTown = root.findViewById(R.id.userTownView);
        editButton = root.findViewById(R.id.saveButton);
        try {
            uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        }catch(Exception e){
            Toast.makeText(getContext(), "You are not signed in", Toast.LENGTH_SHORT).show();
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("profile_pics").child(uid);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String uidReal = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                    DataSnapshot ds = dataSnapshot.child(uidReal);
                    User user = ds.getValue(User.class);
                    if (user != null) {
                        userName.setText(user.getName());
                        userState.setText(user.getState());
                        userBio.setText(user.getBio());
                        userAddress.setText(user.getAddress());
                        userTown.setText(user.getTown());
                        if(!user.getProfilePicUrl().equals("")){
                            Glide.with(userPic.getContext())
                                    .load(user.getProfilePicUrl())
                                    .into(userPic);
                            Log.d(TAG, "SUCCEEDED");
                        }
                        else {
                            Log.d(TAG, "FAILED");
                        }
                    }

                    else {
                        userName.setText("Not Found");
                        userState.setText("Not Found");
                        userBio.setText("Not Found");
                        userAddress.setText("Not Found");
                        userTown.setText("Not Found");
                    }
                }catch(Exception e){
                    Log.d("TAG", "User Error");
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
