package com.digid.eddokenaCM.Synchronisation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InsertEnteteCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InsertEnteteTask;
import com.digid.eddokenaCM.Utils.NotificationManger;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.Commande.CommendeAPI;
import com.digid.eddokenaCM.WebServices.Commande.GetSingleCmdCallback;

public class GetSingleCmdWorker extends Worker implements GetSingleCmdCallback, InsertEnteteCallback {


    String clientName;
    String doPiece;

    public GetSingleCmdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        doPiece = getInputData().getString("doPiece");
        clientName = getInputData().getString("client");

        if (!doPiece.isEmpty()) {
            new CommendeAPI().getSingleCmd(getApplicationContext(), doPiece, SessionManager.getInstance().getToken(getApplicationContext()), this);
        }
        return Result.success();
    }

    @Override
    public void getSingleCmdSuccess(Order order) {
        //NotificationManger.getInstance().infoNotification(getApplicationContext(),"Tesst XX: " +cmdEntete.getDoPiece());
        new InsertEnteteTask(getApplicationContext(), order, this).execute();
    }

    @Override
    public void getSingleCmdFailed() {
        NotificationManger.getInstance().infoNotification(getApplicationContext(), "getSingleCmdFailed : ");

    }

    @Override
    public void onEnteteInsertionError() {

    }

    @Override
    public void onEnteteInsertionSuccess(long id) {
        NotificationManger.getInstance().displaySyncSingleCmdNotification(getApplicationContext(), clientName, doPiece);
    }
}
