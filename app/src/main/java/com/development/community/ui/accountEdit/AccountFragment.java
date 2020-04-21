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

import com.development.community.R;
import com.development.community.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AccountFragment extends Fragment {
    EditText userState,userTown,userAddress,userBio,userName;
    private Button saveButton,photoPicker;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private StorageReference storageReference;
    private FirebaseAuth mFirebaseAuth;



    public AccountFragment(){

    }

    public interface switchActivity{
        void switchActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account_edit, container, false);
        final TextView textView = root.findViewById(R.id.text_messaging);

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


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals("") || userState.getText().toString().equals("") || userTown.getText().toString().equals("") || userAddress.getText().toString().equals("") ||
                        userBio.getText().toString().equals(""))
                    Toast.makeText(getContext(),"Make sure to fill out all fields", Toast.LENGTH_LONG).show();
                else {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(new User(userName.getText().toString(),userState.getText().toString(),userTown.getText().toString(),
                            userAddress.getText().toString(),userBio.getText().toString()));
                    try {
                        switchActivity switchActivity = (switchActivity) getContext();
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
            String uid = mFirebaseAuth.getCurrentUser().getUid();
            StorageReference photoRef = storageReference.child(uid);
            assert selectedImageUri != null;
            photoRef.putFile(selectedImageUri);

        }

    }
}
