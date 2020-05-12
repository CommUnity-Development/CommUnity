package com.development.community.ui.accountEdit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.development.community.EntryActivity;
import com.development.community.MainActivity;
import com.development.community.R;
import com.development.community.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AccountFragment extends Fragment {
    EditText userState,userTown,userAddress,userBio,userName;
    private Button saveButton,photoPicker;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    private FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mFirebaseAuth;
    private String uid;
    final User[] user = new User[1];
    String token;


    public AccountFragment(){

    }



    public interface switchActivity{
        void switchActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("profile_pics");
        mFirebaseAuth = FirebaseAuth.getInstance();
        try {
            uid = Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid();
        }catch(Exception e){
            Toast.makeText(getContext(), "You are not signed in", Toast.LENGTH_LONG).show();
        }

        mAuth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_account_edit, container, false);
//        final TextView textView = root.findViewById(R.id.text_messaging);

        mFirebaseAuth = FirebaseAuth.getInstance();

        userName = root.findViewById(R.id.userNameEnter);
        userState = root.findViewById(R.id.userStateEnter);
        userTown = root.findViewById(R.id.userTownEnter);
        userAddress = root.findViewById(R.id.userAddressEnter);
        userBio = root.findViewById(R.id.userBioEnter);
        photoPicker = root.findViewById(R.id.photoPicker);
        saveButton = root.findViewById(R.id.saveButton);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user[0] = dataSnapshot.child(uid).getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseMessaging.getInstance().subscribeToTopic("updates");
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()) {
                            token = Objects.requireNonNull(task.getResult()).getToken();
                            saveToken(token);
                        }
                    }
                });

                if(userName.getText().toString().equals("") || userState.getText().toString().equals("") || userTown.getText().toString().equals("") || userAddress.getText().toString().equals("") ||
                        userBio.getText().toString().equals(""))
                    Toast.makeText(getContext(),"Make sure to fill out all fields", Toast.LENGTH_LONG).show();
                else {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(new User(firebaseAuth.getCurrentUser().getUid(), userName.getText().toString(),userState.getText().toString(),userTown.getText().toString(),
                            userAddress.getText().toString(),userBio.getText().toString(),token));
                    try {
                        switchActivity switchActivity = (switchActivity) getContext();
                        assert switchActivity != null;
                        switchActivity.switchActivity();
                    }catch(Exception e){
                        Log.d("TAG", "sad");



                    }
                }
            }


        });


        photoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            }
        });




        return root;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1) {

        }
        else if(requestCode == 2){
            Uri selectedImageUri = data.getData();
            try {
                String uid = mFirebaseAuth.getCurrentUser().getUid();
                StorageReference photoRef = storageReference.child(uid);
                assert selectedImageUri != null;
                photoRef.putFile(selectedImageUri);
                user[0].setProfilePicUrl(selectedImageUri.toString());
                databaseReference.child(uid).setValue(user[0]);
            }catch(Exception e){
                Toast.makeText(getContext(), "You are not signed in", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void saveToken(String token){
        String uid = mAuth.getCurrentUser().getUid();
        User user = new User(uid, userName.getText().toString(),userState.getText().toString(),userTown.getText().toString(),
                userAddress.getText().toString(),userBio.getText().toString(),token);

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");

        dbUsers.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(getContext(),"Token Saved",Toast.LENGTH_LONG).show();
            }
        });

    }

}
