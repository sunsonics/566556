package com.example.a566556;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    private static final String CHANNEL_ID = "MyChannelID";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String message = intent.getStringExtra("message");
            if (message != null) {
                startForegroundService(message);
            }
        } catch (Exception e) {
            Log.e("NotificationService", "Error in onStartCommand", e);
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundService(String message) {
        try {
            createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.images)  // Replace with your notification icon
                    .setContentTitle("My Notification")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            Notification notification = builder.build();

            startForeground(NOTIFICATION_ID, notification);
        } catch (Exception e) {
            Log.e("NotificationService", "Error in startForegroundService", e);
        }
    }

    private void createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "My Channel";
                String description = "My Channel Description";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        } catch (Exception e) {
            Log.e("NotificationService", "Error in createNotificationChannel", e);
        }
    }
}
