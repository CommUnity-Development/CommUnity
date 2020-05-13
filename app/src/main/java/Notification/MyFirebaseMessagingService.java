package Notification;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.development.community.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * A class that deals with the local distribution of Notifications rather than sending them out to other devices, this is working
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * sends out a notification
     * @param remoteMessage a remote message to be sent in a notification
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(),title,text);
        }
    }




}
