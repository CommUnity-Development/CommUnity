package com.development.community;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Learned from https://www.youtube.com/watch?v=jXtof6OUtcE

/**
 * StartUp Activity
 * Loads a splash screen for a couple of seconds while the app is loading, then starts MainActivity
 */
public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIME = 1000;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");

    private String userState,userBio,userAddress,userTown;


    /**
     * Runs when the activity (and app) is started
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();


                intent.setClass(Splash.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME);
    }


}

