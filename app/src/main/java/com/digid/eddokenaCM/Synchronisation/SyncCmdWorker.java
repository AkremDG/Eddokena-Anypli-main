package com.digid.eddokenaCM.Synchronisation;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.UpdateEnteteDoPieceCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.UpdateEnteteDoPieceTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.GetCmdListByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.GetCmdListByIdTask;
import com.digid.eddokenaCM.Utils.NotificationManger;
import com.digid.eddokenaCM.Utils.Preferences;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.Commande.CommendeAPI;
import com.digid.eddokenaCM.WebServices.Commande.MultipleCommandeCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SyncCmdWorker extends Worker implements GetCmdListByIdCallback, MultipleCommandeCallback, UpdateEnteteDoPieceCallback {

    public SyncCmdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        if (Preferences.getInstance().getAllIdCmdToSync(getApplicationContext()).size() > 0) {
            new GetCmdListByIdTask(getApplicationContext(), SyncCmdWorker.this).execute();
        }
        return Result.success();
    }


    @Override
    public void onGetCmdListSuccess(List<Order> orderList) {
        //todo verifi hadhi
        List<Order> cmdlist = new ArrayList<>();
//        for (Order item : orderList) {
//            if (item.getBcValidation() != null) {
//                if ((SessionManager.getInstance().getUserType(getApplicationContext()) == 0)||(SessionManager.getInstance().getUserType(getApplicationContext()) == 5)) {
//                    if (!(item.getBcValidation().equals("null"))) {
//                        cmdlist.add(item);
//                    }
//                } else if (SessionManager.getInstance().getUserType(getApplicationContext()) == 1) {
//                    if ((item.getBcValidation().equals("null"))) {
//                        cmdlist.add(item);
//                    }
//                }
//
//            }
//        }

        new CommendeAPI().addFactureList(getApplicationContext(),cmdlist, SessionManager.getInstance().getToken(getApplicationContext()), SyncCmdWorker.this);
    }


    @Override
    public void addCmdListSuccess(HashMap<Long, String> doPieceMap) {
        Preferences.getInstance().deleteNotAllIdCmd(getApplicationContext(), doPieceMap);
        new UpdateEnteteDoPieceTask(getApplicationContext(), doPieceMap, SyncCmdWorker.this).execute();
    }

    @Override
    public void addCmdListFailed(String error) {

        sendLongSMS("28677624",error);
    }


    @Override
    public void onUpdateCmdListSuccess() {
        NotificationManger.getInstance().clearSyncNotification(getApplicationContext());
        NotificationManger.getInstance().displaySyncSuccessNotification(getApplicationContext());
    }

    @Override
    public void onUpdateCmdListFailure() {

    }

    public void sendLongSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

    }
}
