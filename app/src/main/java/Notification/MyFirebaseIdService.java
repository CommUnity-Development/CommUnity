package Notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {


    public void onTokenRefresh(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(firebaseUser != null){
            updateToken(refreshedToken);
        }
    }

    private void updateToken(String refreshedToken){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refreshedToken);
        reference.child(firebaseUser.getUid()).setValue(token);

    }

}
