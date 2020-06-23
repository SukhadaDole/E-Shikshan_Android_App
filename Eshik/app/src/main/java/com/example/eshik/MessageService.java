package com.example.eshik;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title,String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Eshik").setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(false)
                .setContentText((CharSequence)message);





        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }
}
