package com.example.task_streak.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.example.task_streak.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "channel_id"; // Your unique channel ID

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification Channel";
            String description = "Channel for app notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static Notification createNotification(Context context, String title, String message) {
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.not)
                    .setLargeIcon(getLargeIcon(context))
                    .setColorized(true)
                    .setColor(context.getResources().getColor(R.color.colorPrimary)) // Set the color for large icon background
                    .setAutoCancel(true);
        }

        return builder.build();
    }
    private static Bitmap getLargeIcon(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }
}