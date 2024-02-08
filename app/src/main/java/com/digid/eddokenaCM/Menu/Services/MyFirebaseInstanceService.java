package com.digid.eddokenaCM.Menu.Services;

import android.util.Log;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.digid.eddokenaCM.Synchronisation.GetSingleCmdWorker;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseInstanceService extends FirebaseMessagingService {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        SessionManager.getInstance().setRegistrationId(getApplicationContext(), s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (Integer.parseInt(remoteMessage.getData().get("type")) == 10) {
            launchSingleCommandWorker(remoteMessage.getData());
        }
    }

    private void launchSingleCommandWorker(Map<String, String> pushData) {
        // Init your constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        Data.Builder data = new Data.Builder();

        //Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
        data.putString("client", pushData.get("client"));
        data.putString("doPiece", pushData.get("do_piece"));


        //Set Input Data & constraints
        OneTimeWorkRequest internetEeventOneTimeWorkRequest = new OneTimeWorkRequest.Builder(GetSingleCmdWorker.class)
                .setConstraints(constraints)
                .setInputData(data.build())
                .build();

        // Now, enqueue your work
        WorkManager.getInstance().enqueue(internetEeventOneTimeWorkRequest);
    }


}
