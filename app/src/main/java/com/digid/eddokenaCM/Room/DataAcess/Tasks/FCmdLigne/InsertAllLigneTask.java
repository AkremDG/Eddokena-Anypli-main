package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class InsertAllLigneTask extends AsyncTask<Void, Void, long[]> {

    Context context;
    List<OrderItem> orderItemList;
    InsertAllLigneCallback callback;

    public InsertAllLigneTask(Context context, List<OrderItem> orderItemList, InsertAllLigneCallback callback) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.callback = callback;
    }
    @Override
    protected long[] doInBackground(Void... voids) {
        return MyDB.getInstance(context).fCmdLigneDAO().insertAll(orderItemList);
    }

    @Override
    protected void onPostExecute(long[] result) {
        super.onPostExecute(result);

        if (result.length == 1 && result[0] == -1) {
            Log.i("doInBackground", "" + result);
            callback.onLigneInsertionError();
        } else {
            Log.i("historique", "onPostExecute: Y "+ orderItemList.size());
            callback.onLigneInsertionSuccess();
        }
    }
}
