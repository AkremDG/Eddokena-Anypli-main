package com.digid.eddokenaCM.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

import com.digid.eddokenaCM.MainActivity;
import com.digid.eddokenaCM.Menu.Historique.HistoriqueCoFragment;
import com.digid.eddokenaCM.R;

import java.util.Calendar;

public class NotificationManger {
    private static final NotificationManger ourInstance = new NotificationManger();
    public static final int SYNC_NOTIFICATION_ID = 2000;
    public static final int SYNC_SUCCESS_NOTIFICATION_ID = 2001;
    public static final int SYNC_NEW_COMMANDE_ID = 2002;
    public static final int SYNC_REG_NOTIFICATION_ID = 2003;
    public static final int SYNC_REG_SUCCESS_NOTIFICATION_ID = 2004;
    public static final int NEW_NOTIFICATION_ID = 2005;

    private MediaPlayer mp;

    public static NotificationManger getInstance() {
        return ourInstance;
    }

    private NotificationManger() {
    }


    public void displaySyncNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Commande en Attente de Synchronisation")
                .setContentText("Vérifier votre connexion internet")
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(SYNC_NOTIFICATION_ID, notification.build());
    }

    public void displaySyncRegNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Reglement en Attente de Synchronisation")
                .setContentText("Vérifier votre connexion internet")
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(SYNC_REG_NOTIFICATION_ID, notification.build());
    }

    public void clearSyncNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(SYNC_NOTIFICATION_ID);
    }

    public void clearSyncRegNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(SYNC_REG_NOTIFICATION_ID);
    }

    public void displaySyncSuccessNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Synchronisation")
                .setContentText("Commande Synchronisée avec success")
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(SYNC_SUCCESS_NOTIFICATION_ID, notification.build());
    }

    public void displaySyncRegSuccessNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Synchronisation")
                .setContentText("Reglement Synchronisée avec success")
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(SYNC_REG_SUCCESS_NOTIFICATION_ID, notification.build());
    }

    public void clearSyncSuccessNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(SYNC_SUCCESS_NOTIFICATION_ID);
    }

    public void clearSyncRegSuccessNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(SYNC_REG_SUCCESS_NOTIFICATION_ID);
    }


    public void infoNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Info")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify((int) Calendar.getInstance().getTimeInMillis(), notification.build());
    }

    public void displaySyncSingleCmdNotification(Context context, String clientName, String doPiece) {
        mp = MediaPlayer.create(context,R.raw.notificationsound);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("notif", "notif");
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NEW_NOTIFICATION_ID,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Nouvelle Commande")
                .setContentText("Le client " + clientName + " vient de passer une commande : " + doPiece)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        mp.start();

        notificationManager.notify((int) Calendar.getInstance().getTimeInMillis(), notification.build());
    }

    public void clearSyncSingleCmdNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel((int) Calendar.getInstance().getTimeInMillis());
    }


}
