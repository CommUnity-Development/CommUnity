package Notification;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.development.community.EntryActivity;
import com.development.community.R;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, EntryActivity.CHANNEL_ID).setContentTitle(title).setContentText(body).setPriority(0).setSmallIcon(R.drawable.ic_person_black_24dp);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(context);

        notificationMC.notify(1,mBuilder.build());

    }
}
