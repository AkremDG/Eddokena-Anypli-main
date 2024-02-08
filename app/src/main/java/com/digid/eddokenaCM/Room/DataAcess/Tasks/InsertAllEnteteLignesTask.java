package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

public class InsertAllEnteteLignesTask extends AsyncTask<Void, Void, Long> {

    Context context;
    Order order;
    InsertEnteteLignesCallback callback;

    long enteteIdResult = -1;
    int deleteLigneResult = -1;
    int deleteEnteteResult = -1;

    public InsertAllEnteteLignesTask(Context context, Order order, InsertEnteteLignesCallback callback) {
        this.context = context;
        this.order = order;
        this.callback = callback;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        if (order.getLigneList().size() > 0){
            if (order.getLigneList().get(0).getIdCmdLocal() != null){
                deleteEnteteResult = MyDB.getInstance(context).fCmdEnteteDAO().delete(order);
                deleteLigneResult = MyDB.getInstance(context).fCmdLigneDAO().deleteAllByID(order.getIdOrder());
            }
        }

        Log.i("rrrrrrrrr", "doInBackground: " +order.getDoDate());
        Log.i("rrrrrrrrr", "doInBackground: " +order.getCoNo());
        Log.i("rrrrrrrrr", "doInBackground: " +order.getClientId());
        Log.i("rrrrrrrrr", "doInBackground: " +order.getIdBo());
        Log.i("rrrrrrrrr", "doInBackground: " +order.getStatus());

        enteteIdResult = MyDB.getInstance(context).fCmdEnteteDAO().insert(order);
        for (int i = 0; i < order.getLigneList().size(); i++) {
            order.getLigneList().get(i).setIdCmdLocal(enteteIdResult);
        }

        long[] linesResult = MyDB.getInstance(context).fCmdLigneDAO().insertAll(order.getLigneList());

        if (enteteIdResult > 0 && linesResult.length > 0 && linesResult[0] > 0) {
            return 1l;
        } else {
            return 0l;
        }

    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        if (result == -1) {
            callback.ontEnteteLignesInsertError();
        } else {
            callback.ontEnteteLignesInsertSucces(enteteIdResult);
        }
    }
}
