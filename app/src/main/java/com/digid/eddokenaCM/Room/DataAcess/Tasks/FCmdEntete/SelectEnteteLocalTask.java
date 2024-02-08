package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Order;

import java.util.List;

public class SelectEnteteLocalTask extends AsyncTask<Void, Void, List<Order>> {

    Context context;
    SelectEnteteLocalCallback callback;

    public SelectEnteteLocalTask(Context context, SelectEnteteLocalCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<Order> doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(List<Order> orderList) {
        super.onPostExecute(orderList);
        callback.onLocalEnteteSelectionSuccess(orderList);
    }
}
