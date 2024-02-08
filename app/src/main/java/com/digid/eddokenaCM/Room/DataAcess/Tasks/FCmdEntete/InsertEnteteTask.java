package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

public class InsertEnteteTask extends AsyncTask<Void, Void, Long> {

    Context context;
    Order order;
    InsertEnteteCallback callback;

    public InsertEnteteTask(Context context, Order order, InsertEnteteCallback callback) {
        this.context = context;
        this.order = order;
        this.callback = callback;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return MyDB.getInstance(context).fCmdEnteteDAO().insert(order);
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        if (result == -1) {
            callback.onEnteteInsertionError();
        } else {
            callback.onEnteteInsertionSuccess(result);
        }

    }
}
