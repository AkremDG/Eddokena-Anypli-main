package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

public class UpdateAllEnteteLignesTask extends AsyncTask<Void, Void, Long> {

    Context context;
    Order order;
    UpdateAllEnteteLignesCallback callback;

    long enteteIdResult = -1;
    long ligneDeleteResult = -1;
    long enteteDeleteResult = -1;

    public UpdateAllEnteteLignesTask(Context context, Order order, UpdateAllEnteteLignesCallback callback) {
        this.context = context;
        this.order = order;
        this.callback = callback;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        enteteDeleteResult = MyDB.getInstance(context).fCmdEnteteDAO().delete(order);
        enteteIdResult = MyDB.getInstance(context).fCmdEnteteDAO().insert(order);
        for (int i = 0; i < order.getLigneList().size(); i++) {
            order.getLigneList().get(i).setIdCmdLocal(enteteIdResult);
            ligneDeleteResult = MyDB.getInstance(context).fCmdLigneDAO().delete(order.getLigneList().get(i));

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

        if (result == 0) {
            callback.ontEnteteLignesUpdateError();
        } else {
            callback.ontEnteteLignesUpdateSucces(enteteIdResult);
        }
    }
}
