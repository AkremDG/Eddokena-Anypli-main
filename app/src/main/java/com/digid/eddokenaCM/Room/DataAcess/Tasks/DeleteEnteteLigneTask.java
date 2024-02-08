package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

public class DeleteEnteteLigneTask extends AsyncTask<Void, Void, Long> {

    Context context;
    Order order;
    DeleteEnteteLigneCallback callback;

    long enteteIdResult = -1;
    long ligneDeleteResult = -1;
    long enteteDeleteResult = -1;

    public DeleteEnteteLigneTask(Context context, Order order, DeleteEnteteLigneCallback callback) {
        this.context = context;
        this.order = order;
        this.callback = callback;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        enteteDeleteResult = MyDB.getInstance(context).fCmdEnteteDAO().delete(order);
        ligneDeleteResult = MyDB.getInstance(context).fCmdLigneDAO().deleteByID(order.getIdOrder());


        if (enteteDeleteResult >= 0 && ligneDeleteResult >= 0) {
            return 1l;

        } else {
            return 0l;
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        if (result == 0) {
            callback.ontEnteteLignesDeleteError();

        } else {
            callback.ontEnteteLignesDeleteSucces();
        }
    }
}