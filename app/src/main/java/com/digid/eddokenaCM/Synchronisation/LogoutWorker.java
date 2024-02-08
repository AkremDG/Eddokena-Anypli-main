package com.digid.eddokenaCM.Synchronisation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.digid.eddokenaCM.Utils.SessionManager;

public class LogoutWorker extends Worker {

    private NavController navController;


    public LogoutWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {

        SessionManager.getInstance().setUserType(getApplicationContext(), -1);
        SessionManager.getInstance().setToken(getApplicationContext(), "#");
        SessionManager.getInstance().setloginDate(getApplicationContext());
        SessionManager.getInstance().setUserId(getApplicationContext(), "-1");

        return Result.success();
    }
}
