package com.zqhy.app.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.text.Html;

import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/29
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";


    public NotificationUtils(Context base) {
        super(base);
    }


    NotificationChannel channel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public void sendNotification(int id, Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            getManager().notify(id, notification);
        } else {
            getManager().notify(id, notification);
        }
    }


    public void cancelNotification(int id) {
        getManager().cancel(id);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification createDownloadNotification(String contentTitle, String contentText, int maxProgress, int progress) {
        if (channel != null) {
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        }

        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setProgress(maxProgress, progress, false)
                .setSmallIcon(R.mipmap.ic_placeholder, Notification.FLAG_NO_CLEAR)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
    }


    public Notification createDownloadNotification_25(String contentTitle, String contentText, int maxProgress, int progress) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setProgress(maxProgress, progress, false)
                .setSmallIcon(R.mipmap.ic_placeholder, Notification.FLAG_NO_CLEAR)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification createPushNotification(String contentTitle, String contentText, PendingIntent pendingIntent) {
        if (channel != null) {
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 300, 200, 500});
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        }
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(Html.fromHtml(contentTitle))
                .setContentText(Html.fromHtml(contentText))
                .setSmallIcon(R.mipmap.ic_placeholder)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }


    public Notification createPushNotification_25(String contentTitle, String contentText, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(Html.fromHtml(contentTitle))
                .setContentText(Html.fromHtml(contentText))
                .setSmallIcon(R.mipmap.ic_placeholder)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setVibrate(new long[]{0, 300, 200, 500})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }


}
