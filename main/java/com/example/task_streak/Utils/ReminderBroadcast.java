package com.example.task_streak.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Trigger the notification here
        String title = "Reminder";
        String message = "Have Some Water";
        NotificationHelper.createNotification(context, title, message);

        showNotification(context, title, message);

    }

    private void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Ensure the notification channel is created
        NotificationHelper.createNotificationChannel(context);

        // Create the notification using the NotificationHelper
        Notification notification = NotificationHelper.createNotification(context, title, message);

        int notificationId = (int) System.currentTimeMillis();

        // Issue the notification
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }
}