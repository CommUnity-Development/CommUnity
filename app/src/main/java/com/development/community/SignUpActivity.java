package com.development.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private Button button;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("test");

        button = findViewById(R.id.signUpButton);

        String id = Objects.requireNonNull(getIntent().getExtras()).getString("ID");
        assert id != null;
        final DatabaseReference dr = databaseReference.child(id);

        final int[] status = new int[1];

        dr.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status[0] = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                if(status[0] == 0) button.setText("Sign Up");
                else if(status[0] == 1) button.setText("Withdraw");
                else button.setText("Sign Up");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(button.getText().equals("Sign Up"))
                dr.child("status").setValue(1);
                else if(button.getText().equals("Withdraw"))
                    dr.child("status").setValue(0);
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
