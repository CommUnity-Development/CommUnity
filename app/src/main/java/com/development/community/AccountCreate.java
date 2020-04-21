package com.development.community;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AccountCreate extends AppCompatActivity {

    private EditText userName;
    private EditText userState;
    private EditText userTown;
    private EditText userAddress;
    private EditText userBio;

    private Button createButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mFirebaseAuth;

    private Button photoPicker;
    final User[] user = new User[1];
    String uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try {
            uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        }catch(Exception e){
            Toast.makeText(AccountCreate.this, "You are not signed in", Toast.LENGTH_LONG).show();
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user[0] = dataSnapshot.child(uid).getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        photoPicker = findViewById(R.id.photoPicker);

        userName = findViewById(R.id.userName);
        userState = findViewById(R.id.userState);
        userTown = findViewById(R.id.userTown);
        userAddress = findViewById(R.id.userAddress);
        userBio = findViewById(R.id.userBioEnter);

        createButton = findViewById(R.id.createButton);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        storageReference = mFirebaseStorage.getReference().child("profile_pics");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountCreate.this, MainActivity.class);
                if(userName.getText().toString().equals("") || userState.getText().toString().equals("") || userTown.getText().toString().equals("") || userAddress.getText().toString().equals("") ||
                        userBio.getText().toString().equals(""))
                    Toast.makeText(AccountCreate.this,"Make sure to fill out all fields", Toast.LENGTH_LONG).show();
                else {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(new User(userName.getText().toString(),userState.getText().toString(),userTown.getText().toString(),
                            userAddress.getText().toString(),userBio.getText().toString()));
                    startActivity(intent);
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if(requestCode == 2 && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            try {
                String uid = Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid();
                StorageReference photoRef = storageReference.child(uid);
                assert selectedImageUri != null;
                photoRef.putFile(selectedImageUri);
                user[0].setProfilePicUrl(selectedImageUri.toString());
                databaseReference.child(uid).setValue(user[0]);
            }catch(Exception e){
                Toast.makeText(AccountCreate.this, "You are not signed in", Toast.LENGTH_SHORT).show();
            }

        }

    }

    }
