package com.development.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class SignUpActivity extends AppCompatActivity {

    private Button button;
    private Button markButton;
    ImageView taskStatus;
    private int count = 0;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private static final String CHANNEL_ID = "community";
    private static final String CHANNEL_NAME = "CommUnity";
    private static final String CHANNEL_DESC = "CommUnity Notifications";

    /**
     * Runs when the activity is started
     * Allows the user to sign up for an activity, mark as complete, mark as incomplete, or withdraw
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");

        button = findViewById(R.id.signUpButton);
        markButton = findViewById(R.id.markAsCompleteButton);
        taskStatus = findViewById(R.id.taskStatus);


        String id = Objects.requireNonNull(getIntent().getExtras()).getString("ID");
        assert id != null;
        final DatabaseReference dr = databaseReference.child(id);

        final int[] status = new int[1];

        dr.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status[0] = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                if(status[0] == 0) button.setText("Sign Up");
                else if(status[0] == 1) {
                    button.setText("Withdraw");
                    markButton.setVisibility(0);
                }
                else {
                    button.setText("Mark as incomplete");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                // Signs up the user for the task
                if(button.getText().equals("Sign Up")) {
                    dr.child("status").setValue(1);
                    assert user != null;
                    Log.d("DISPLAY", user.getDisplayName());
                    Log.d("DISPLAY", user.getUid());
                    dr.child("serverUsername").setValue(user.getDisplayName());
                    dr.child("serverUID").setValue(user.getUid());
                    displayNotification(0);
                }

                // Withdraws the task
                else if(button.getText().equals("Withdraw")) {
                    dr.child("status").setValue(0);
                    dr.child("serverUsername").setValue(null);
                    dr.child("serverUID").setValue(null);
                    displayNotification(2);
                }
                else{
                    // Marks a task as incomplete
                    dr.child("status").setValue(1);
                    displayNotification(3);
                }
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        // Marks a task as complete
        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr.child("status").setValue(2);
                displayNotification(1);
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                count++;
            }
        });


    }

    public boolean isComplete(){
        if(count%1 == 0)
            return true;

        return false;
    }


    private void displayNotification(int type){
        NotificationCompat.Builder mBuilder;
        if(type == 0) {
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Successfully Signed Up for a Task").setPriority(0).setSmallIcon(R.drawable.ic_arrow_upward_black_24dp);
        }
        else if(type == 1) {
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Successfully Marked Task as Complete").setPriority(0).setSmallIcon(R.drawable.ic_check_black_24dp);
        }
        else if(type == 2){
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Successfully Withdrew Task").setPriority(0).setSmallIcon(R.drawable.ic_remove_circle_black_24dp);
        }
        else{
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Successfully Marked Task as Incomplete").setPriority(0).setSmallIcon(R.drawable.ic_backspace_black_24dp);

        }
        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(this);

        notificationMC.notify(1,mBuilder.build());

    }
}
