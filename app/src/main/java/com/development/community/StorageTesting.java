package com.development.community;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

/**
 * This class was originally used to test Storage, as in getting a user to select and upload a profile picture
 * After this was successful consistently we implemented this in the app
 */
public class StorageTesting extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    private ImageButton photoPicker;


    /**
     * Runs when the activity is opened and allows the users to insert a photo to be uploaded
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_storage_testing);
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseStorage = FirebaseStorage.getInstance();
//
//        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
//        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("profile_pics");
//
//        photoPicker = findViewById(R.id.photoPicker);
//
//        photoPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/jpeg");
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
//            }
//        });
//
//    }

    /**
     *
     * @param requestCode an integer to tell is the request is good to go
     * @param resultCode an integer to tell if the result is good to go
     * @param data an Intent to get data from the users device
     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode == 1) {
//            if (resultCode == RESULT_OK)
//                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
//            else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//            else if(requestCode == 2 && resultCode == RESULT_OK){
//                Uri selectedImageUri = data.getData();
//                try {
//                    String uid = Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid();
//                    StorageReference photoRef = mChatPhotosStorageReference.child(uid);
//                    assert selectedImageUri != null;
//                    photoRef.putFile(selectedImageUri);
//                }catch(Exception e){
//                    Toast.makeText(StorageTesting.this, "You are not signed in", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//    }
}
