package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);


        userName = findViewById(R.id.userName);
        userState = findViewById(R.id.userState);
        userTown = findViewById(R.id.userTown);
        userAddress = findViewById(R.id.userAddress);
        userBio = findViewById(R.id.userBio);

        createButton = findViewById(R.id.createButton);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

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
    }



    }
