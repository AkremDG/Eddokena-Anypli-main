package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;
import com.digid.eddokenaCM.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SellectAllEnteteTask extends AsyncTask<Void, Void, List<Order>> {

    Context context;
    long id;
    String doTiers;
    long profil;
    SellectAllEnteteCallback callback;

    public SellectAllEnteteTask(Context context, long id, String doTiers, long profil, SellectAllEnteteCallback callback) {
        this.context = context;
        this.id = id;
        this.doTiers = doTiers;
        this.profil = profil;
        this.callback = callback;
    }

    @Override
    protected List<Order> doInBackground(Void... voids) {
        try {
            return MyDB.getInstance(context).fCmdEnteteDAO().findAllByIdCo(id, SessionManager.getInstance().getBD(context));

        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<Order>();
        }

    }

    @Override
    protected void onPostExecute(List<Order> orderList) {
        super.onPostExecute(orderList);
        callback.onEnteteSelectionSuccess(orderList);
    }
}
