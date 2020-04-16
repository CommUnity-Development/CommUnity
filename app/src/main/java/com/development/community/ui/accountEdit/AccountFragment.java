package com.development.community.ui.accountEdit;

import android.os.Bundle;
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

import java.util.Objects;

public class AccountFragment extends Fragment {
    EditText userState,userTown,userAddress,userBio,userName;
    Button saveButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public AccountFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account_edit, container, false);
        final TextView textView = root.findViewById(R.id.text_messaging);

        userName = root.findViewById(R.id.userNameEnter);
        userState = root.findViewById(R.id.userStateEnter);
        userTown = root.findViewById(R.id.userTownEnter);
        userAddress = root.findViewById(R.id.userAddressEnter);
        userBio = root.findViewById(R.id.userBioEnter);

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
                }
            }
        });



        return root;


    }
}
