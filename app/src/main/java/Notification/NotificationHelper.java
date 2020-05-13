package Notification;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.development.community.EntryActivity;
import com.development.community.R;

/**
 * A class that helps when sending notification so we do not have to keep copy and pasting,
 * just need to call displayNotification
 */
public class NotificationHelper {

    /**
     * Displays the notification with the given parameters
     * @param context the context in which it is used
     * @param title the title of the notification
     * @param body the body text of the notification
     */
    public static void displayNotification(Context context, String title, String body){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, EntryActivity.CHANNEL_ID).setContentTitle(title).setContentText(body).setPriority(0).setSmallIcon(R.drawable.ic_person_black_24dp);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(context);

        notificationMC.notify(1,mBuilder.build());

    }
}
